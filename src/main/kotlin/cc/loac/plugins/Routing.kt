package cc.loac.plugins

import cc.loac.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * 配置博客路由
 */
fun Application.configureRouting() {
    routing {
        userRouting()
    }
}
