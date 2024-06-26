package cc.loac.utils

import cc.loac.data.responses.MyResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

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