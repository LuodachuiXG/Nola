package cc.loac.data.sql.tables

import cc.loac.data.models.enums.OperationType
import org.jetbrains.exposed.sql.Table

/**
 * 操作记录表
 */
object Operations : Table("operation") {

    /** 操作记录表 ID **/
    val operationId = long("operation_id").autoIncrement()

    /** 操作用户 ID **/
    val userId = long("user_id")

    /** 操作用户名 **/
    val username = varchar("username", 64, "utf8mb4_general_ci")

    /** 操作类型 **/
    val operationType = enumerationByName("operation_type", 24, OperationType::class)

    /** 操作描述 **/
    val operationDesc = varchar("operation_desc", 512).nullable()

    /** 操作日期时间戳 **/
    val createTime = long("create_time")

    override val primaryKey = PrimaryKey(operationId)
}