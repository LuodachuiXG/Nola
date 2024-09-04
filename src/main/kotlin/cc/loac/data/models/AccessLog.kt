package cc.loac.data.models

import cc.loac.data.models.enums.AccessLogType

/**
 * 访问日志数据类
 */
data class AccessLog(
    /** 访问日志 ID */
    val accessLogId: Long,
    /** 访问路径 */
    val path: String,
    /** 访问 IP */
    val ip: String,
    /** 访问时间 */
    val accessTime: Long,
    /** 访问日志类型 */
    val type: AccessLogType,
    /** 消息 */
    val message: String?
)
