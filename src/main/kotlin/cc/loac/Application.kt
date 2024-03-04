package cc.loac

import cc.loac.plugins.*
import cc.loac.data.sql.DatabaseSingleton
import cc.loac.security.token.JwtTokenService
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    // 初始化 JWT 及相关认证服务
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        // Token 令牌过期时间 3 小时
        expiresIn = 3 * 1000 * 60 * 60,
        secret = environment.config.property("jwt.secret").getString()
    )

    // 初始化数据库
    DatabaseSingleton.init()
    // 接口访问速率限制配置
    configureRateLimit()
    // 跨域配置
    configureCORS()
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
