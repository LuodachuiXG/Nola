package cc.loac.data.sql.tables

import cc.loac.data.models.enums.AccessLogType
import org.jetbrains.exposed.sql.Table

/**
 * 访问日志表
 */
object AccessLogs : Table("access_log") {

    /** 访问日志 ID **/
    val accessLogId = long("access_log_id").autoIncrement()
    /** 访问路径 **/
    val path = varchar("path", 256)
    /** 访问 IP **/
    val ip = varchar("ip", 64)
    /** 访问时间 **/
    val accessTime = long("access_time")
    /** 访问日志类型 **/
    val type = enumerationByName("type", 24, AccessLogType::class)
    /** 消息 **/
    val message = varchar("message", 256).nullable()

    override val primaryKey = PrimaryKey(accessLogId)
}