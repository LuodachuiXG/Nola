package cc.loac.utils

import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.extensions.isBoolean
import cc.loac.extensions.isInt
import cc.loac.extensions.isPositiveInt
import cc.loac.extensions.toJSON
import cc.loac.security.token.TokenClaim
import com.fasterxml.jackson.databind.JsonNode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.lettuce.core.dynamic.annotation.Param

/**
 * 根据数据类获取请求的参数
 * 如果参数不匹配或条件不满足，就抛出参数不匹配异常
 * @param predicate 断言，如果不满足条件就抛出参数不匹配异常
 */
suspend inline fun <reified T> ApplicationCall.receiveByDataClass(
    predicate: (T) -> Boolean = { true }
): T {
    val request = runCatching {
        this.receiveNullable<T>()
    }.getOrNull() ?: run {
        throw ParamMismatchException()
    }
    // 如果不满足条件，也抛出参数不匹配异常
    if (!predicate(request)) throw ParamMismatchException()
    return request
}

/**
 * 根据名称获取请求的参数
 * 如果参数不匹配，就抛出参数不匹配异常
 * 此方法只能接收 x-www-form-urlencoded 的参数
 * @param names 请求参数名数组
 */
suspend fun ApplicationCall.receiveByName(vararg names: String): Parameters {
    val params = this.receiveParameters()
    names.forEach { name ->
        if (!params.contains(name)) {
            // 参数名不存在
            // 抛出参数不匹配异常
            throw ParamMismatchException()
        }
    }
    return params
}

/**
 * 根据名称获取请求的参数 Map，此方法适用 JSON 传参
 * 如果参数不匹配，或参数值为空，就抛出参数不匹配异常
 * @param names 请求参数数据类数组
 */
suspend fun ApplicationCall.receiveMapByName(
    vararg names: RequestParam
): Map<String, String?> {
    val requestText = this.receiveText()
    val json = requestText.toJSON()
    json.error()
    val map = mutableMapOf<String, String?>()
    names.forEach { name ->
        val node = json[name.paramName]?.textValue()
        // 当前参数设置不可为空，但是实际传参为空
        if (!name.nullable && node == null) {
            throw ParamMismatchException()
        }
        map[name.paramName] = node
    }
    return map
}

/**
 * 根据名称获取请求的参数 Map，此方法适用 JSON 传参
 * 如果参数不匹配，或参数值为空，就抛出参数不匹配异常
 * @param names 请求参数名数组
 */
suspend fun ApplicationCall.receiveMapByName(
    vararg names: String
): Map<String, String?> {
    val list = mutableListOf<RequestParam>()
    names.forEach { name ->
        list.add(name.nonNull())
    }
    // * 将数组转为可变数量的参数
    return receiveMapByName(*list.toTypedArray())
}

/**
 * 接收 JsonNode 对象
 */
suspend fun ApplicationCall.receiveJSON(): JsonNode {
    val text = this.receiveText()
    return text.toJSON()
}

/**
 * 接收路径传参多个参数
 * 该方法只能接收路径传参形式，如 get("/{id}/{name}?sort=DESC")
 * @param paramNames 请求参数名数组
 */
fun ApplicationCall.receivePathParams(
    vararg paramNames: String
): Map<String, String> {
    val map = mutableMapOf<String, String>()
    val params = this.parameters
    paramNames.forEach { paramName ->
        val value = params[paramName]
        // 如果参数不存在，抛出参数不匹配异常
        value ?: throw ParamMismatchException()
        map[paramName] = value
    }
    return map
}

/**
 * 接收路径传参单个参数
 * @param paramName 请求参数名
 */
fun ApplicationCall.receivePathParam(
    paramName: String,
): String {
    val params = this.parameters
    val value = params[paramName]
    // 如果参数不存在，抛出参数不匹配异常
    value ?: throw ParamMismatchException()
    return value
}

/**
 * 接收可为空的路径传参单个参数
 * @param paramName 请求参数名
 * @param predicate 参数值是否满足条件
 */
fun ApplicationCall.receiveNullablePathParam(
    paramName: String,
    predicate: (String?) -> Boolean? = { true }
): String? {
    val params = this.parameters
    val value = params[paramName]
    // 如果参数不满足条件，就抛出参数不匹配异常
    val p = predicate(value)
    // 如果 predicate 结果为空，就直接返回空
    p ?: return null
    // 如果 predicate 结果为 false，就抛出参数不匹配异常
    if (!p) throw ParamMismatchException()
    return value
}

/**
 * 接收路径布尔型传参
 * 如果对应参数不为空，并且不为布尔型，就抛出参数不匹配异常
 * @param paramName 请求参数名
 * @param defaultValue 默认值
 */
fun ApplicationCall.receiveNullableBooleanPathParam(
    paramName: String,
    defaultValue: Boolean = false
): Boolean {
    val params = receiveNullablePathParam(paramName)
    // 如果参数为空，直接返回默认值
    if (params.isNullOrEmpty()) {
        return defaultValue
    }
    // 参数不为空，并且不为布尔值，返回参数不匹配异常
    if (!params.isBoolean()) throw ParamMismatchException()
    return params.toBoolean()
}

/**
 * 接收路径整数型传参
 * 如果对应参数不为整数，就抛出参数不匹配异常
 * @param paramName 请求参数名
 */
fun ApplicationCall.receiveIntPathParam(
    paramName: String
): Int {
    val value = receivePathParam(paramName)
    // 如果不为整数，就抛出参数不匹配异常
    if (!value.isInt()) throw ParamMismatchException()
    return value.toInt()
}

/**
 * 接收路径 Long 类型传参
 * 如果对应参数不为 Long，就抛出参数不匹配异常
 * @param paramName 请求参数名
 */
fun ApplicationCall.receiveLongPathParam(
    paramName: String
): Long {
    val value = receivePathParam(paramName)
    // 如果不为非零正整数，就抛出参数不匹配异常
    if (!value.isPositiveInt()) throw ParamMismatchException()
    return value.toLong()
}


/**
 * 接收用于分页的页数和页面条数
 * 如果 page 为空或 0，page 和 size 都回调 0
 */
suspend fun ApplicationCall.receivePageAndSize(
    block: suspend (page: Int, size: Int) -> Unit
) {
    // 获取路径传参
    var page = receiveNullablePathParam("page")
    if (page.isNullOrEmpty()) page = "0"
    // 如果 page 为 0，size 也为 0
    val size = if (page == "0") "0" else receivePathParam("size")
    // page 和 size 是否为负数
    if (!page.isPositiveInt(true) || !size.isPositiveInt(true)) {
        // 抛出参数不匹配异常
        throw ParamMismatchException()
    }
    // 如果 page 不为 0，size 为 0，抛出参数不匹配异常
    if (page != "0" && size == "0") {
        throw ParamMismatchException()
    }
    block(page.toInt(), size.toInt())
}

/**
 * 获取请求的 Token 的 Claim
 * @param tokenClaim Claim 枚举
 */
fun ApplicationCall.getTokenClaim(tokenClaim: TokenClaimEnum): TokenClaim? {
    val principal = this.principal<JWTPrincipal>()
    return principal?.getClaim(tokenClaim.key, String::class)?.let { value ->
        TokenClaim(name = tokenClaim, value = value)
    }
}

/**
 * 从 Token 中获取用户 ID 和用户名
 * @return Pair<用户 ID, 用户名>
 */
fun ApplicationCall.getUser(): Pair<Long, String> {
    val userId = this.getTokenClaim(TokenClaimEnum.USER_ID)?.value?.toLongOrNull() ?: -1
    val username = this.getTokenClaim(TokenClaimEnum.USERNAME)?.value ?: "未知用户"
    return userId to username
}

/**
 * 获取请求的用户的 IP 地址
 */
fun ApplicationCall.ip(): String {
    return this.request.headers["X-Forwarded-For"] ?: this.request.origin.remoteAddress
}

/**
 * 从请求头中获取 Token
 */
fun ApplicationCall.getToken(): String? {
    return this.request.headers["Authorization"]?.removePrefix("Bearer ")?.trim()
}

/**
 * 请求参数数据类
 * @param paramName 请求的参数名
 * @param nullable 参数是否可为空
 */
data class RequestParam(
    val paramName: String,
    val nullable: Boolean = false
)

/**
 * 将一个 String 字符串封装成可为空的 [RequestParam] 数据类
 */
fun String.nullable() = RequestParam(this, true)

/**
 * 将一个 String 字符串封装成不可为空的 [RequestParam] 数据类
 */
fun String.nonNull() = RequestParam(this)