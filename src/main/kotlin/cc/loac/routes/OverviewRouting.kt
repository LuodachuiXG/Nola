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
import io.ktor.server.routing.head
import io.ktor.server.routing.route
import io.ktor.server.sse.heartbeat
import io.ktor.server.sse.sse
import io.ktor.server.websocket.webSocket
import io.ktor.sse.ServerSentEvent
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import org.koin.java.KoinJavaComponent.inject
import java.util.UUID
import kotlin.time.Duration.Companion.seconds


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

            /** 管理端 SSE 订阅当前博客现在人数 **/
            sse("online") {

                // 心跳包保持连接
                heartbeat {
                    period = 30.seconds
                    event = ServerSentEvent("ping")
                }

                val sessionId = UUID.randomUUID().toString()
                val manager = BlogOnlineManager.getInstance()

                try {
                    manager.addSseSubscriber(sessionId, this)
                    // 保持连接直到断开
                    awaitCancellation()
                } catch (_: Exception) {
                } finally {
                    manager.removeSseSubscriber(sessionId)
                }
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