package cc.loac.plugins

import cc.loac.data.exceptions.MyException
import cc.loac.data.responses.respondFail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

/**
 * 配置状态页面插件
 * 用于指定不同的异常返回对应的数据
 */
fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondFail("404 Not Found.", status)
        }

        exception<MyException> { call, e ->
            call.respondFail(e.message!!)
        }
    }
}