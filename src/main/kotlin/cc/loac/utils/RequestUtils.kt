package cc.loac.utils

import cc.loac.data.exceptions.ParamMismatchException
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*

/**
 * 根据数据类获取请求的参数
 * 如果参数不匹配，就抛出参数不匹配异常
 */
suspend inline fun <reified T> ApplicationCall.receiveByDataClass(): T {
    val request = runCatching {
        this.receiveNullable<T>()
    }.getOrNull() ?: run {
        throw ParamMismatchException()
    }
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
): Map<String, String> {
    val requestText = this.receiveText()
    val json = requestText.toJSON()
    val map = mutableMapOf<String, String>()
    names.forEach { name ->
        val node = json[name.paramName]?.textValue() ?: throw ParamMismatchException()
        // 当前参数设置不可为空，但是实际传参为空
        if (!name.nullable && node.isBlank()) throw ParamMismatchException()
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
): Map<String, String> {
    val list = mutableListOf<RequestParam>()
    names.forEach { name ->
        list.add(name.nonNull())
    }
    // * 将数组转为可变数量的参数
    return receiveMapByName(*list.toTypedArray())
}

/**
 * 获取请求的 Token 的 Claim
 */
suspend fun ApplicationCall.getTokenClaim(name: String): String? {
    val principal = this.principal<JWTPrincipal>()
    return principal?.getClaim(name, String::class)
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