package cc.loac.utils

import cc.loac.data.files.config.TencentCOSConfig
import com.qcloud.cos.model.Bucket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

private val ioScope = CoroutineScope(Dispatchers.IO)
private val mainScope = CoroutineScope(Dispatchers.Main)

/**
 * 启动 IO 协程
 * @param block 协程体
 */
fun launchIO(block: suspend () -> Unit) {
    ioScope.launch { block() }
}

/**
 * 启动主线程协程
 * @param block 协程体
 */
fun launchMain(block: suspend () -> Unit) {
    mainScope.launch { block() }
}


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
    fileGroupName: String?
): String {
    return "https://" + StringBuilder().apply {
        append(config.bucket)
        append(".cos.")
        append(config.region)
        append(".myqcloud.com/")
        append("${config.path}/")
        append(fileGroupName ?: "")
        append("/$fileName")
    }.toString().replaceDoubleSlash()
}