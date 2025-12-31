package cc.loac.utils

import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.extensions.replaceDoubleSlash
import kotlin.random.Random

/**
 * 生成指定长度的随机字符
 * 包括数字或字母
 * @param length 长度
 */
fun randomString(length: Int): String {
    val chars = "0123456789abcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder()
    for (i in 0 until length) {
        sb.append(chars[Random.nextInt(chars.length)])
    }
    return sb.toString()
}

/**
 * 拼接腾讯云对象存储文件链接地址
 */
fun tencentCOSUrl(
    config: TencentCOSConfig,
    fileName: String,
    fileGroupPath: String?
): String {
    return "https://" + StringBuilder().apply {
        append(config.bucket)
        append(".cos.")
        append(config.region)
        append(".myqcloud.com/")
        append("${config.path}/")
        append(fileGroupPath ?: "")
        append("/$fileName")
    }.toString().replaceDoubleSlash()
}