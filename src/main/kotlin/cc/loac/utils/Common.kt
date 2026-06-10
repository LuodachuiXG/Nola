package cc.loac.utils

import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.extensions.replaceDoubleSlash
import kotlin.random.Random

/** 随机字符串字符集：数字 + 小写字母 + 大写字母，共 62 个字符 */
private const val RANDOM_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

/**
 * 生成指定长度的随机字符
 * 包括数字或字母（大小写）
 * @param length 长度
 */
fun randomString(length: Int): String {
    return (1..length).map { RANDOM_CHARS[Random.nextInt(RANDOM_CHARS.length)] }.joinToString("")
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