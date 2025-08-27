package cc.loac.utils

import cc.loac.extensions.toJSONString
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 日志组件
 */
object LogUtil {
    private val logger: Logger by lazy {
        LoggerFactory.getLogger("Nola")
    }

    /**
     * 打印 info 日志
     */
    fun info(msg: Any?) {
        logger.info(stringFromAny(msg))
    }

    /**
     * 打印 error 日志
     */
    fun error(msg: Any?) {
        logger.error(stringFromAny(msg))
    }

    /**
     * 将 Any? 类型转为 String 字符串类型
     * @param msg Any? 类型消息
     * @return 返回转换后的字符串消息
     */
    private fun stringFromAny(msg: Any?): String {
        if (msg == null) return "null"
        return when (msg) {
            is String -> msg
            else -> msg.toJSONString()
        }
    }
}

/**
 * 消息日志扩展函数
 */
fun Any?.info() {
    LogUtil.info(this)
}

/**
 * 错误日志扩展函数
 */
fun Any?.error() {
    LogUtil.error(this)
}
