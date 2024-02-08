package cc.loac

import cc.loac.plugins.*
import cc.loac.data.DatabaseSingleton
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.security.token.JwtTokenService
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    // 初始化数据库
    DatabaseSingleton.init()

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 24 * 1000 * 60 * 60,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    configureSecurity(tokenConfig)
    configureSerialization()
    configureRouting(hashingService, tokenService, tokenConfig)
    configureStatusPage()
}
