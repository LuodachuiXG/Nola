package cc.loac.data.redis

import io.ktor.server.config.*
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Redis 服务单例
 */
object RedisSingleton {

    private val scope = CoroutineScope(Dispatchers.IO)

    // Redis Lettuce 客户端
    private var redisClient: RedisClient? = null

    // Redis 连接
    private var connection: StatefulRedisConnection<String, String>? = null

    // Redis Lettuce 实例（支持 Kotlin 协程）
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    lateinit var instance: RedisCoroutinesCommands<String, String>
        private set

    /**
     * 初始化 Redis Lettuce 实例
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun init(config: ApplicationConfig) {
        val redisURL = config.property("ktor.redis.redisURL").getString()
        val client = RedisClient.create(redisURL)
        val conn = client.connect()
        redisClient = client
        connection = conn
        instance = conn.coroutines()
    }

    /**
     * 检查是否已经实例化
     * @throws IllegalStateException
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    private fun checkInstance() {
        if (!this::instance.isInitialized) {
            // 实例还未初始化
            throw IllegalStateException("请先调用 init() 实例化。")
        }
    }

    /**
     * 设置键值对（同步）
     * @param key 键
     * @param value 值
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    suspend fun setSync(key: String, value: String): String? {
        checkInstance()
        return instance.set(key, value)
    }

    /**
     * 设置带过期时间的键值对（同步）
     * @param key 键
     * @param value 值
     * @param ttlSeconds 过期时间（秒）
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    suspend fun setWithExpirySync(key: String, value: String, ttlSeconds: Long): String? {
        checkInstance()
        return instance.setex(key, ttlSeconds, value)
    }

    /**
     * 设置键值对（异步）
     * @param key 键
     * @param value 值
     * @param callback 回调事件
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun set(
        key: String,
        value: String,
        callback: (String?) -> Unit = {}
    ) {
        checkInstance()
        scope.launch {
            callback(instance.set(key, value))
        }
    }

    /**
     * 获取数据（同步）
     * @param key 键
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    suspend fun getSync(key: String): String? {
        checkInstance()
        return instance.get(key)
    }

    /**
     * 获取数据（异步）
     * @param key 键
     * @param callback 回调事件
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun get(key: String, callback: (String?) -> Unit) {
        checkInstance()
        scope.launch {
            callback(instance.get(key))
        }
    }

    /**
     * 删除键（同步）
     * @param key 键
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    suspend fun deleteSync(key: String): Long? {
        checkInstance()
        return instance.del(key)
    }

    /**
     * 关闭 Redis（同步）
     * 先关闭连接，再关闭客户端线程池，确保无重连干扰
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun closeSync() {
        // 1. 先关连接（同时会使 instance 失效）
        connection?.close()
        connection = null
        // 2. 再关闭客户端（关闭线程池，包括已禁用的看门狗）
        redisClient?.shutdown()
        redisClient = null
    }
}
