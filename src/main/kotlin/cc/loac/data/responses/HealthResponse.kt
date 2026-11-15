package cc.loac.data.responses

/**
 * 健康检查响应
 * @param status 服务整体状态：UP / DEGRADED
 * @param database 数据库是否可用
 * @param redis Redis 是否可用
 * @param time 服务器时间戳
 */
data class HealthResponse(
    val status: String,
    val database: Boolean,
    val redis: Boolean,
    val time: Long
)
