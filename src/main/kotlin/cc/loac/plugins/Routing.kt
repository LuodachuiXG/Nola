package cc.loac.plugins

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
        userRouting(hashingService, tokenService, tokenConfig)
    }
}
