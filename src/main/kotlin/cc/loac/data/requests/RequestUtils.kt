package cc.loac.data.requests

import cc.loac.data.exceptions.ParamMismatchException
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*
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
 * 根据名称获取请求的参数 Map
 * 此方法适用 JSON 传参
 * 如果参数不匹配，就抛出参数不匹配异常
 * @param names 请求参数名数组
 */
suspend fun ApplicationCall.receiveMapByName(vararg names: String): Map<String, String> {
    val requestText = this.receiveText()
    val om = ObjectMapper()
    val json = om.readTree(requestText)
    val map = mutableMapOf<String, String>()
    names.forEach { name ->
        val node = json[name] ?: throw ParamMismatchException()
        map[name] = node.textValue()
    }
    return map
}