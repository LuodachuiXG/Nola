package cc.loac.plugins


import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureWebSocket() {
    install(WebSockets) {
        // ping 之间的持续时间
        pingPeriod = 30.seconds

        // 超时时间
        timeout = 15.seconds

        // 帧最大大小
        maxFrameSize = Long.MAX_VALUE

        // 是否使用掩码
        masking = false
    }
}