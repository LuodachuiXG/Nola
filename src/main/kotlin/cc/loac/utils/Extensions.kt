package cc.loac.utils

import cc.loac.security.hashing.HashingService
import cc.loac.security.hashing.SHA256HashingService
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Safelist
import org.koin.java.KoinJavaComponent.inject

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
 * @param regex 正则表达式
 * @param find 是否是查找模式
 */
fun String.matches(regex: String, find: Boolean = false): Boolean {
    val pattern = Pattern.compile(regex)
    val matcher = pattern.matcher(this)
    return if (find) {
        matcher.find()
    } else {
        matcher.matches()
    }
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
 * @param includeZero 是否包含零
 */
fun String.isPositiveInt(includeZero: Boolean = false): Boolean {
    if (includeZero) return this.matches("^[0-9]\\d*")
    return this.matches("^[1-9]\\d*")
}

/**
 * String 扩展函数
 * 验证是否是整数
 */
fun String.isInt(): Boolean {
    return this.matches("^\\d+")
}

/**
 * String 扩展函数
 * 验证是否是布尔值
 */
fun String.isBoolean(): Boolean {
    return this.matches("^(true|false)$")
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

/**
 * String 扩展函数
 * 将字符串转为 SHA256 哈希
 */
fun String?.sha256Hex(): String? {
    this ?: return null
    val hashingService: HashingService by inject(HashingService::class.java)
    return hashingService.generatedHash(this)
}

/**
 * String 扩展函数
 * 判断当前字符是否可以转换成对应枚举类（是否是枚举类的成员）
 */
inline fun <reified T : Enum<T>> String.isEnum(): Boolean {
    return this in T::class.java.enumConstants.map { it.toString() }
}

/**
 * String 扩展函数
 * 用于在文件名后，文件扩展名前面添加上五个随机字符
 * 用于在文件名已存在时，防止文件名重复
 * 如果该文件名后面已经有随机字符，则重新修改该随机字符为新的随机字符
 */
fun String.addRandomSuffix(): String {
    // 先获取文件的扩展名
    val fileExtension = this.substringAfterLast(".", "")
    // 获取文件名
    val fileName = this.substringBeforeLast(".", this)
    val randomStr = randomString(5)
    // 如果文件名最后已经有了 "_" 加五个随机字符的后缀，则重新修改该后缀
    // 先获取文件名最后六个字符，判断是否符合随机字符规则
    return if (fileName.length >= 6 &&
        fileName.substring(fileName.length - 6).matches("^_[a-z0-9]+\$")
    ) {
        // 符合规则，则重新修改该后缀
        fileName.substring(0, fileName.length - 6) + "_$randomStr.$fileExtension"
    } else {
        // 不符合，则直接在文件名后面添加上随机字符
        "${fileName}_$randomStr.$fileExtension"
    }
}

/**
 * String 扩展函数
 * 将所有双正反斜杠替换为单斜杠
 */
fun String.replaceDoubleSlash(): String {
    return this
        .replace("//", "/")
        .replace("\\\\", "/")
}

/**
 * 格式化斜杠
 * 把所有反斜杠替换为正斜杠
 */
fun String.formatSlash(): String =
    this.replace("\\", "/")
        .replaceDoubleSlash()


/**
 * String 扩展函数
 * 将汉字转为拼音
 */
fun String.toPinyin(): String {
    var pinyinStr = ""
    // 去掉当前文本中的所有的非数字、非字母、非汉字文本
    val newText = this.replace("[^0-9a-zA-Z\\u4e00-\\u9fa5]".toRegex(), "")
    val newChar = newText.toCharArray()
    val defaultFormat = HanyuPinyinOutputFormat()
    defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
    defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
    newChar.forEach { char ->
        if (char > 128.toChar()) {
            try {
                pinyinStr += PinyinHelper.toHanyuPinyinStringArray(char, defaultFormat)[0]
            } catch (e: BadHanyuPinyinOutputFormatCombination) {
                e.printStackTrace()
            }
        } else {
            pinyinStr += char
        }
    }
    return pinyinStr
}

/**
 * 将文章名称转为别名
 */
fun String.postName2Slug(): String {
    // 先将中文转拼音
    val pinyin = this.toPinyin()
    // 将所有空白字符替换成 -，并且 转小写
    return pinyin.replace("\\s\\W+".toRegex(), "-").lowercase()
}