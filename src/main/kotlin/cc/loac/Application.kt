package cc.loac

import cc.loac.data.redis.RedisSingleton
import cc.loac.plugins.*
import cc.loac.data.sql.DatabaseSingleton
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*

lateinit var globalEnvironment: ApplicationEnvironment

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    // Token 配置
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        // Token 令牌过期时间 3 小时
        expiresIn = 3 * 1000 * 60 * 60,
        secret = environment.config.property("jwt.secret").getString()
    )
    globalEnvironment = environment

    // 初始化数据库
    DatabaseSingleton.init(environment.config)
    // 初始化 Redis
//    RedisSingleton.init(environment.config)
    // WebSocket 配置
    configureWebSocket()
    // 模板引擎配置
    configureThymeleaf()
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
    configureRouting(tokenConfig)
    // 状态页面配置（异常拦截器）
    configureStatusPage()
    // 监视器插件配置
//    configureMonitorPlugin()
}
