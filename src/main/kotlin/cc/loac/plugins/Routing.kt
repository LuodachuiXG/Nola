package cc.loac.plugins

import cc.loac.routes.*
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import cc.loac.utils.error
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * 配置博客路由
 */
fun Application.configureRouting(
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        // 静态文件
        staticResources("/", "/static")
        /** 后台接口 **/
        route("/admin") {
            // user 用户相关路由
            userRouting(tokenService, tokenConfig)
            // config 博客配置管理员路由
            configAdminRouting()
            // tag 标签管理员路由
            tagAdminRouting()
            // category 分类管理员路由
            categoryAdminRouting()
        }

        /** 博客接口 **/
        route("/api") {
            // config 博客配置 API 路由
            configApiRouting()
            // tag 标签 API 路由
            tagApiRouting()
            // category 分类 API 路由
            categoryApiRouting()
        }
    }
}
