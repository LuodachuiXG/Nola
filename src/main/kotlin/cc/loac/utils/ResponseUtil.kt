package cc.loac.utils

import cc.loac.data.responses.MyResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * 失败响应
 * @param status HTTP 状态码
 */
suspend fun ApplicationCall.respondFailure(
    status: HttpStatusCode = HttpStatusCode.Conflict
) {
    val response = MyResponse(
        code = status.value,
        errMsg = status.description,
        data = null
    )
    respond(status, response)
}

/**
 * 失败响应
 * @param errMsg 错误消息
 * @param status HTTP 状态码
 */
suspend fun ApplicationCall.respondFailure(
    errMsg: String,
    status: HttpStatusCode = HttpStatusCode.Conflict
) {
    val response = MyResponse(
        code = status.value,
        errMsg = errMsg,
        data = null
    )
    respond(status, response)
}

/**
 * 成功响应
 * @param data 数据
 * @param status HTTP 状态码
 */
suspend fun <T> ApplicationCall.respondSuccess(
    data: T?,
    status: HttpStatusCode = HttpStatusCode.OK
) {
    val response = MyResponse(
        code = status.value,
        errMsg = null,
        data = data
    )
    respond(status, response)
}