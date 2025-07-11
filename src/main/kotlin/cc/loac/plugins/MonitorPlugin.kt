package cc.loac.plugins

import cc.loac.data.models.AccessLog
import cc.loac.data.models.enums.AccessLogType
import cc.loac.services.AccessLogService
import cc.loac.utils.info
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

/**
 * 监视器插件配置
 */
class MonitorPluginConfiguration {
    /**
     * 忽视的路径列表，如：/assets
     */
    var ignoreStartWith = emptyList<String>()

    val handleList = mutableListOf<suspend (ApplicationCall, String) -> Unit>()

    fun handleUri(handler: suspend (ApplicationCall, String) -> Unit) {
        handleList.add(handler)
    }
}

/**
 * 自定义监视器插件
 */
val MonitorPlugin = createApplicationPlugin(
    name = "MonitorPlugin",
    createConfiguration = ::MonitorPluginConfiguration
) {
    "MonitorPlugin is running".info()
    onCall { call ->
        call.request.origin.apply {
            if (pluginConfig.ignoreStartWith.isEmpty()) {
                // 监视所有路径
                pluginConfig.handleList.forEach {
                    it(call, uri)
                }
            } else {
                // 只监视指定路径
                val ignore = pluginConfig.ignoreStartWith.find { uri.startsWith(it) }
                ignore ?: pluginConfig.handleList.forEach {
                    it(call, uri)
                }
            }
        }
    }
}

/**
 * 配置监视器插件
 */
fun Application.configureMonitorPlugin() {
    val accessLogService: AccessLogService by inject(AccessLogService::class.java)

    install(MonitorPlugin) {
        ignoreStartWith = listOf("/assets", "/upload", "/console/assets")
        handleUri { call, uri ->
            // X-Real-IP 是 Nginx 反向代理设置的客户端真实 IP 头
            val ip = call.request.headers["X-Real-IP"]

            accessLogService.addAccessLog(
                AccessLog(
                    accessLogId = -1,
                    path = uri,
                    ip = ip ?: "0.0.0.0",
                    accessTime = Date().time,
                    type = when {
                        uri.startsWith("/api/post/content") -> AccessLogType.POST
                        uri == "/" -> AccessLogType.BLOG
                        else -> AccessLogType.URI
                    },
                    message = null
                )
            )
        }
    }
}