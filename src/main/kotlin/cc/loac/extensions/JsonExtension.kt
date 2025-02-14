package cc.loac.extensions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * String 扩展函数
 * 将字符串转为 JsonNode 对象
 */
fun String.toJSON(): JsonNode {
    val om = jacksonObjectMapper()
    return om.readTree(this)
}

/**
 * String 扩展函数
 * 将 JSON 字符串反序列化为具体类
 */
inline fun <reified T> String.jsonToClass(): T {
    val om = jacksonObjectMapper()
    return om.readValue(this, T::class.java)
}


/**
 * Any 扩展函数
 * 将任意对象转为 JsonNode 对象
 */
fun Any.toJSON(): JsonNode {
    return this.toJSONString().toJSON()
}

/**
 * Any 扩展函数
 * 将任意对象转为 JSON 字符串
 */
fun Any.toJSONString(): String {
    val om = jacksonObjectMapper()
    return om.writeValueAsString(this)
}