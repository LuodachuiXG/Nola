package cc.loac.data.responses

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * 响应类数据类
 * @param code 响应代码
 * @param errMsg 错误信息
 * @param data 响应数据
 */
@Serializable
data class MyResponse<T> (
    val code: Int,
    val errMsg: String?,
    val data: T?
)

suspend fun ApplicationCall.respondFail(
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
 * 封装响应方法
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