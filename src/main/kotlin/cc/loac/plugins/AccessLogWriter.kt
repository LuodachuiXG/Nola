package cc.loac.plugins

import cc.loac.services.AccessLogService
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.inject

/**
 * 启动访问日志异步写入任务
 * 依赖 Koin 初始化完成，必须在 configureKoin 之后调用
 */
fun Application.configureAccessLogWriter() {
    val accessLogService: AccessLogService by inject(AccessLogService::class.java)
    accessLogService.startWriter()
}
