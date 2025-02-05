package cc.loac.routes

import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*


/**
 * 模板引擎路由
 */
fun Route.templateRouting() {
    get("/") {
        call.respondTemplate("index")
    }
}