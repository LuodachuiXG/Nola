package cc.loac.plugins

import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.responses.respondFailure
import cc.loac.utils.error
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*

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
            "${call.request.host()} 无权访问受保护资源：${call.request.uri}".error()
            call.respondFailure("无权访问受保护资源", status)
        }

        /** MyException 异常 **/
        exception<MyException> { call, e ->
            "${call.request.host()} 自定义异常：${e.message}".error()
            call.respondFailure(e.message ?: "Unknown Error")
        }

        /** 参数不匹配异常 **/
        exception<ParamMismatchException> { call, e ->
            "${call.request.host()} 请求参数不匹配：${e.message}".error()
            call.respondFailure("请求参数不匹配", HttpStatusCode.Conflict)
        }

        /** 默认异常类 **/
        exception<Exception> { call, e ->
            e.printStackTrace()
            call.respondFailure("未知错误，请查看服务端日志", HttpStatusCode.InternalServerError)
        }
    }
}