package cc.loac.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * 将日期格式化为 2024-07-28 的文本格式
 */
fun Long.formatDate(): String {
    return Date().formatDate()
}

/**
 * 将日期格式化为 2024-07-28 的文本格式
 */
fun Date.formatDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(this)
}