package cc.loac

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.enums.ConfigKey
import cc.loac.plugins.*
import cc.loac.data.sql.DatabaseSingleton
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.security.token.JwtTokenService
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    // 初始化数据库
    DatabaseSingleton.init()

    // 初始化 JWT 及相关认证服务
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        // Token 令牌过期时间 3 小时
        expiresIn = 3 * 1000 * 60 * 60,
        secret = System.getenv("JWT_SECRET")
    )


    // Koin 依赖注入配置
    configureKoin()
    // JWT 验证配置
    configureSecurity(tokenConfig)
    // 序列化器配置
    configureSerialization()
    // 路由配置
    configureRouting(tokenService, tokenConfig)
    // 状态页面配置（异常拦截器）
    configureStatusPage()
}
