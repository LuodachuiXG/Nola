package cc.loac.plugins

import cc.loac.routes.configAdminRouting
import cc.loac.routes.configApiRouting
import cc.loac.routes.userRouting
import cc.loac.security.hashing.HashingService
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * 配置博客路由
 */
fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        /** 后台接口 **/
        route("/admin") {
            // user 用户相关路由
            userRouting(hashingService, tokenService, tokenConfig)
            // config 博客配置信息路由
            configAdminRouting()
        }

        /** 博客接口 **/
        route("/api") {
            configApiRouting()
        }
    }
}
