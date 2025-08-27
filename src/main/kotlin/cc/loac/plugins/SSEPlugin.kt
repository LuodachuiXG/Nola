package cc.loac.plugins

import io.ktor.server.application.*
import io.ktor.server.sse.*

/**
 * 配置 SSE 插件
 */
fun Application.configureSSE() {
    install(SSE) {}
}
