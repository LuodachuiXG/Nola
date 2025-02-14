package cc.loac.data.redis

import io.ktor.server.config.*
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Redis 服务单例
 */
object RedisSingleton {

    private val scope by lazy {
        CoroutineScope(Dispatchers.IO)
    }

    // Redis Lettuce 实例（支持 Kotlin 协程）
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    lateinit var instance: RedisCoroutinesCommands<String, String>

    /**
     * 初始化 Redis Lettuce 实例
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun init(config: ApplicationConfig) {
        val redisURL = config.property("ktor.redis.redisURL").getString()
        val redisClient = RedisClient.create(redisURL)
        val connection = redisClient.connect()
        instance = connection.coroutines()
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
        return instance.set(key, value)
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
        return instance.get(key)
    }

    /**
     * 获取数据（异步）
     * @param key 键
     * @param callback 回调事件
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun get(key: String, callback: (String?) -> Unit) {
        scope.launch {
            callback(instance.get(key))
        }
    }

    /**
     * 关闭 Redis（异步）
     * @param onFinish 完成回调（可选）
     */
    @OptIn(ExperimentalLettuceCoroutinesApi::class)
    fun close(onFinish: () -> Unit = {}) {
        scope.launch {
            instance.shutdown(false)
            onFinish()
        }
    }
}