package cc.loac.data.models

import cc.loac.data.models.enums.OperationType
import java.util.Date

/**
 * 操作记录数据类
 * @param operationId 操作记录 ID
 * @param userId 操作用户 ID
 * @param username 操作用户名
 * @param operationType 操作类型
 * @param operationDesc 操作描述
 * @param createTime 操作时间（创建时间）
 */
data class Operation(
    val operationId: Long = -1,
    val userId: Long = -1,
    val username: String,
    val operationType: OperationType,
    val operationDesc: String? = null,
    val createTime: Long = Date().time
)