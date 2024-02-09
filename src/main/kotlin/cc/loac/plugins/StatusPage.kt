package cc.loac.plugins

import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.responses.respondFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

/**
 * 配置状态页面插件
 * 用于指定不同的异常或状态时的响应
 */
fun Application.configureStatusPage() {
    val log = this.environment.log
    install(StatusPages) {
        /** 404 Not Found 状态 **/
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondFailure("404 Not Found.", status)
        }

        /** 401 未授权状态 **/
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respondFailure("无权访问受保护资源", status)
        }

        /** MyException 异常 **/
        exception<MyException> { call, e ->
            call.respondFailure(e.message ?: "Unknown Error")
        }

        /** 参数不匹配异常 **/
        exception<ParamMismatchException> { call, _ ->
            call.respondFailure("请求参数不匹配", HttpStatusCode.Conflict)
        }

        exception<Exception> { call, e ->
            e.printStackTrace()
            call.respondFailure("未知错误", HttpStatusCode.InternalServerError)
        }
    }
}