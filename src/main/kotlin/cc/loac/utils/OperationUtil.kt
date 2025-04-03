package cc.loac.utils

import cc.loac.data.models.Operation
import cc.loac.data.models.enums.OperationType
import cc.loac.services.OperationService
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

private val operationService: OperationService by inject(OperationService::class.java)

private val ioScope = CoroutineScope(Dispatchers.IO)

/**
 * 记录操作记录（同步）
 * @param desc 操作描述
 * @param userId 用户 ID
 * @param username 用户名
 * @param isHighRisk 是否高危操作
 */
suspend fun operateSync(
    desc: String,
    userId: Long,
    username: String,
    isHighRisk: Boolean = false
) {
    operationService.addOperation(
        Operation(
            userId = userId,
            username = username,
            operationType = if (isHighRisk) OperationType.HIGH_RISK else OperationType.LOW_RISK,
            operationDesc = desc
        )
    )
}

/**
 * 记录操作记录（异步）
 * @param desc 操作描述
 * @param userId 用户 ID
 * @param username 用户名
 * @param isHighRisk 是否高危操作
 */
fun operate(
    desc: String,
    userId: Long,
    username: String,
    isHighRisk: Boolean = false
) {
    ioScope.launch {
        operateSync(desc, userId, username, isHighRisk)
    }
}

/**
 * 记录操作记录（异步）
 * @param desc 操作描述
 * @param call ApplicationCall（用于从 Token 中获取用户信息）
 * @param isHighRisk 是否高危操作
 */
fun operate(
    desc: String,
    call: ApplicationCall,
    isHighRisk: Boolean = false
) {
    val user = call.getUser()
    operate(desc, user.first, user.second, isHighRisk)
}