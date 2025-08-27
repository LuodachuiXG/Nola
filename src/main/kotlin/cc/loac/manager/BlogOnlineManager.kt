package cc.loac.manager

import cc.loac.extensions.toJSONString
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * 博客前端在线人数数量管理
 */
class BlogOnlineManager private constructor() {

    companion object {
        @Volatile
        private var instance: BlogOnlineManager? = null

        private var ioScope = CoroutineScope(Dispatchers.IO)

        // 定时任务
        private var job: Job? = null

        /**
         * 获取 Manager 实例
         * @return 唯一 BlogOnlineManager 实例
         */
        fun getInstance(): BlogOnlineManager {
            return instance ?: synchronized(this) {
                instance ?: BlogOnlineManager().also {
                    instance = it


                    if (job == null) {
                        // 启动定时任务
                        job = ioScope.launch {
                            // 每 30 分钟尝试清理一次无效连接
                            while (true) {
                                delay(1000 * 60 * 30)
                                instance?.cleanupDeadConnections()
                            }
                        }
                    }
                }
            }
        }
    }


    private val connectionMutex = Mutex()

    // 当前连接的 Session
    private val connections = ConcurrentHashMap<String, WebSocketSession>()

    // 在线人数
    private val onlineCount = AtomicInteger(0)

    // IP 连接计数，防止单个 IP 过多连接
    private val ipConnectionCount = ConcurrentHashMap<String, AtomicInteger>()

    // 每个 IP 最多只计算 5 个设备
    private val maxConnectionsPerIp = 5

    /**
     * 添加连接
     * @param sessionId Session ID
     * @param session WebSocketSession
     * @param clientIp 客户端 IP
     * @return 是否添加成功（如果当前 IP 数量超过 5 个，则认为失败）
     */
    suspend fun addConnection(
        sessionId: String,
        session: WebSocketSession,
        clientIp: String
    ): Boolean {
        return connectionMutex.withLock {
            // 检查单个 IP 连接数限制
            val ipCount = ipConnectionCount.computeIfAbsent(clientIp) { AtomicInteger(0) }
            if (ipCount.get() >= maxConnectionsPerIp) {
                return@withLock false
            }

            connections[sessionId] = session
            ipCount.incrementAndGet()
            val currentCount = onlineCount.incrementAndGet()

            // 广播新的在线人数
            broadcastOnlineCount(currentCount)
            true
        }
    }

    /**
     * 移除连接
     * @param sessionId Session ID
     * @param clientIp 客户端 IP
     */
    suspend fun removeConnection(sessionId: String, clientIp: String) {
        return connectionMutex.withLock {
            connections.remove(sessionId)
            ipConnectionCount[clientIp]?.decrementAndGet()
            val currentCount = onlineCount.decrementAndGet()

            // 广播新的在线人数
            broadcastOnlineCount(currentCount)
        }
    }

    /**
     * 广播在线人数
     * @param count 在线人数
     */
    private suspend fun broadcastOnlineCount(count: Int) {
        val message = OnlineCount(
            count = count,
            timestamp = System.currentTimeMillis()
        ).toJSONString()

        val deadConnections = mutableListOf<String>()

        connections.forEach { (sessionId, session) ->
            try {
                session.send(message)
            } catch (_: Exception) {
                // 记录死连接，稍后清理
                deadConnections.add(sessionId)
            }
        }

        // 清理死连接
        deadConnections.forEach { sessionId ->
            connections.remove(sessionId)
            onlineCount.decrementAndGet()
        }
    }

    /**
     * 获取当前连接数量
     */
    fun getCurrentCount(): Int = onlineCount.get()

    /**
     * 清理无效连接
     */
    suspend fun cleanupDeadConnections() {
        connectionMutex.withLock {
            val deadConnections = mutableListOf<String>()

            connections.forEach { (sessionId, session) ->
                try {
                    // 发送心跳检测
                    session.send("ping")
                } catch (_: Exception) {
                    deadConnections.add(sessionId)
                }
            }

            deadConnections.forEach { sessionId ->
                connections.remove(sessionId)
                onlineCount.decrementAndGet()
            }
        }
    }
}

/**
 * 在线人数响应类
 * @param count 人数
 * @param timestamp 时间戳毫秒
 */
data class OnlineCount(
    val count: Int,
    val timestamp: Long
)