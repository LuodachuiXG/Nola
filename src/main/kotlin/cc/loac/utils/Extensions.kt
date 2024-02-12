package cc.loac.utils

import cc.loac.data.exceptions.MyException
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.regex.Pattern

val logger: Logger = LoggerFactory.getLogger("Ktor-Logger")

/**
 * String 扩展函数
 * 打印 info 日志
 */
fun String.info() {
    logger.info(this)
}

/**
 * String 扩展函数
 * 打印 error 日志
 */
fun String.error() {
    logger.error(this)
}

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

/**
 * String 扩展函数
 * 验证当前 String 字符串是否是邮箱
 */
fun String.isEmail(): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    val pattern = Pattern.compile(emailRegex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * String 扩展函数
 * 验证当前 String 字符串是否是由字母和数字组成
 */
fun String.isAlphaAndNumeric(): Boolean {
    val pattern = Pattern.compile("^[A-Za-z0-9]+$")
    val matcher = pattern.matcher(this)
    return matcher.matches()
}
