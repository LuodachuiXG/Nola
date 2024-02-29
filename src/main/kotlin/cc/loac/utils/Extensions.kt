package cc.loac.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Safelist

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
 * 验证正则表达式
 */
fun String.matches(regex: String): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

/**
 * String 扩展函数
 * 验证是否是邮箱
 */
fun String.isEmail(): Boolean {
    return this.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
}

/**
 * String 扩展函数
 * 验证是否是由字母和数字组成
 */
fun String.isAlphaAndNumeric(): Boolean {
    return this.matches("^[A-Za-z0-9]+\$")
}

/**
 * String 扩展函数
 * 验证是否是十六进制颜色值
 */
fun String.isHexColor(): Boolean {
    return this.matches("^#([0-9a-fA-F]{6})\$")
}

/**
 * String 扩展函数
 * 验证是否是非零正整数
 */
fun String.isPositiveInt(): Boolean {
    return this.matches("^[1-9]\\d*")
}

/**
 * String 扩展函数
 * 将 Markdown 转为 HTML
 */
fun String.markdownToHtml(): String {
    val parser = Parser.builder().build()
    val document = parser.parse(this)
    val renderer = HtmlRenderer.builder().build()
    return renderer.render(document)
}

/**
 * String 扩展函数
 * 将 HTML 转为纯文本
 * @param ignoreWrap 忽略换行
 */
fun String.htmlToPlainText(ignoreWrap: Boolean = true): String {
    if (this.isEmpty()) return ""
    val document = Jsoup.parse(this)
    val outputSettings = Document.OutputSettings().prettyPrint(false)
    document.outputSettings(outputSettings)
    val newHtml = if (ignoreWrap)
        document.html().replace(Regex("\\s"), "")
    else
        document.html()

    val plainText = Jsoup.clean(newHtml, "", Safelist.none(), outputSettings).trim()
    return plainText
}

/**
 * String 扩展函数
 * 将 Markdown 转为纯文本
 */
fun String.markdownToPlainText(): String {
    return this.markdownToHtml().htmlToPlainText()
}
