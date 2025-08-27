package cc.loac.routes

import cc.loac.manager.BlogOnlineManager
import cc.loac.plugins.LIMITER_ONLINE_COUNT_WB
import cc.loac.services.OverviewService
import cc.loac.utils.error
import cc.loac.utils.getUser
import cc.loac.utils.respondSuccess
import io.ktor.server.auth.authenticate
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID


private val overviewService: OverviewService by inject(OverviewService::class.java)

/**
 * 概览，管理员路由
 */
fun Route.overviewAdminRouting() {
    route("/overview") {
        authenticate {
            get {
                val userId = call.getUser().first
                call.respondSuccess(
                    overviewService.getOverview(
                        userId = userId
                    )
                )
            }
        }
    }
}

/**
 * 概览，API 路由
 */
fun Route.overviewApiRouting() {
    route("/overview") {
        /** 当前博客页面在线人数 WebSocket **/
        rateLimit(LIMITER_ONLINE_COUNT_WB) {
            webSocket("online") {
                val sessionId = UUID.randomUUID().toString()
                val clientIp = call.request.origin.remoteHost
                val manager = BlogOnlineManager.getInstance()

                // 验证连接
                if (!manager.addConnection(sessionId, this, clientIp)) {
                    close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "来自 $clientIp 的连接太多"))
                    return@webSocket
                }

                try {
                    // 处理客户端消息
                    for (frame in incoming) {
                        when (frame) {
                            is Frame.Text -> {
                                // 回复客户端心跳消息
                                val text = frame.readText()
                                if (text == "ping") {
                                    send(Frame.Text("pong"))
                                }
                            }
                            is Frame.Close -> {
                                break
                            }
                            else -> {}
                        }
                    }

                } catch (_: ClosedReceiveChannelException) {
                    // 连接正常关闭
                } catch (e: Exception) {
                    e.localizedMessage.error()
                } finally {
                    manager.removeConnection(sessionId, clientIp)
                }
            }
        }
    }
}