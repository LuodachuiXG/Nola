package cc.loac

import cc.loac.data.redis.RedisSingleton
import cc.loac.data.sql.DatabaseSingleton
import cc.loac.manager.BlogOnlineManager
import cc.loac.plugins.*
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

var globalEnvironment: ApplicationEnvironment? = null
    private set

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

    // 生产环境拒绝默认弱配置启动
    validateProductionConfiguration()

    // 每个 Application 实例独立的应用级协程作用域
    val applicationScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // 初始化数据库
    DatabaseSingleton.init(environment.config)
    // 初始化 Redis
    RedisSingleton.init(environment.config)
    // WebSocket 配置
    configureWebSocket()
    // SSE 配置
    configureSSE()
    // 模板引擎配置
    configureThymeleaf()
    // 可信反向代理配置（必须在速率限制之前）
    configureTrustedProxies()
    // 接口访问速率限制配置
    configureRateLimit()
    // 跨域配置
    configureCORS()
    // 请求体大小限制
    configureRequestBodyLimit()
    // Koin 依赖注入配置
    configureKoin(applicationScope)
    // 访问日志异步写入任务
    configureAccessLogWriter()
    // 定时备份任务
    configureBackupScheduler()
    // JWT 验证配置
    configureSecurity(tokenConfig)
    // 序列化器配置
    configureSerialization()
    // 安全响应头
    configureSecurityHeaders()
    // 路由配置
    configureRouting(tokenConfig)
    // 状态页面配置（异常拦截器）
    configureStatusPage()
    // 监视器插件配置
    configureMonitorPlugin()

    // 应用停止时取消协程作用域并释放数据库、Redis、在线状态
    monitor.subscribe(ApplicationStopped) {
        applicationScope.cancel()
        BlogOnlineManager.reset()
        DatabaseSingleton.close()
        RedisSingleton.closeSync()
    }
}
