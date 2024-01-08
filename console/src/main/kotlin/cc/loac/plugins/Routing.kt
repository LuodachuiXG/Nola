package cc.loac.plugins

import cc.loac.routes.articleRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * 配置项目路由
 */
fun Application.configureRouting() {
    routing {
        articleRoute()
    }
}
