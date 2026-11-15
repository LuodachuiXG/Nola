package cc.loac.services.impl

import cc.loac.data.models.AccessLog
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.AccessLogDao
import cc.loac.extensions.isInt
import cc.loac.globalEnvironment
import cc.loac.services.AccessLogService
import cc.loac.utils.error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 访问日志服务接口实现类
 */
class AccessLogServiceImpl : AccessLogService {

    private val accessLogDao: AccessLogDao by inject(AccessLogDao::class.java)
    private val ioScope: CoroutineScope by inject(CoroutineScope::class.java)

    private val writerStarted = AtomicBoolean(false)
    private val pendingLogs = Channel<AccessLog>(
        capacity = 2048,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun startWriter() {
        if (!writerStarted.compareAndSet(false, true)) return
        ioScope.launch {
            while (isActive) {
                delay(1000)
                val batch = mutableListOf<AccessLog>()
                while (batch.size < 500) {
                    val log = pendingLogs.tryReceive().getOrNull() ?: break
                    batch += log
                }
                if (batch.isEmpty()) continue
                runCatching {
                    accessLogDao.addAccessLogs(batch)
                    trimLogs()
                }.onFailure {
                    "写入访问日志失败：${it.message}".error()
                }
            }
        }
    }

    override fun enqueueAccessLog(accessLog: AccessLog) {
        startWriter()
        pendingLogs.trySend(accessLog)
    }

    /**
     * 增加访问日志
     * @param accessLog 访问日志数据类
     */
    override suspend fun addAccessLog(accessLog: AccessLog): AccessLog? {
        val result = accessLogDao.addAccessLog(accessLog)
        trimLogs()
        return result
    }

    /**
     * 删除访问日志
     * @param accessLogId 访问日志 ID
     */
    override suspend fun deleteLog(accessLogId: Long): Boolean {
        return accessLogDao.deleteLog(accessLogId)
    }

    /**
     * 批量删除访问日志
     * @param accessLogIds 访问日志 ID 集合
     */
    override suspend fun deleteLogs(accessLogIds: List<Long>): Boolean {
        if (accessLogIds.isEmpty()) return true
        return accessLogDao.deleteLogs(accessLogIds)
    }

    /**
     * 删除最早的指定数量的日志
     * @param count 要删除的日志数量
     */
    override suspend fun deleteEarliestLogs(count: Int): Boolean {
        if (count <= 0) return true
        return accessLogDao.deleteEarliestLogs(count)
    }

    /**
     * 删除指定时间以前的日志
     * @param time 时间戳（毫秒）
     */
    override suspend fun deleteBefore(time: Long): Boolean {
        return accessLogDao.deleteBefore(time)
    }

    /**
     * 删除全部访问日志
     */
    override suspend fun deleteAll(): Boolean {
        return accessLogDao.deleteAll()
    }

    /**
     * 获取访问日志
     * @param accessLogId 访问日志 ID
     */
    override suspend fun accessLog(accessLogId: Long): AccessLog? {
        return accessLogDao.accessLog(accessLogId)
    }

    /**
     * 分页获取所有访问日志
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun accessLogs(page: Int, size: Int): Pager<AccessLog> {
        return accessLogDao.accessLogs(page, size)
    }

    /**
     * 获取日志总长度
     */
    override suspend fun count(): Long {
        return accessLogDao.count()
    }

    /**
     * 超过配置上限时删除最早日志
     */
    private suspend fun trimLogs() {
        val maxLength = maxLogLength()
        val currentCount = count()
        if (currentCount > maxLength) {
            deleteEarliestLogs((currentCount - maxLength).coerceAtMost(Int.MAX_VALUE.toLong()).toInt())
        }
    }

    private fun maxLogLength(): Int {
        val configured = globalEnvironment?.config?.propertyOrNull("accessLog.maxLength")
            ?.getString()
            ?.takeIf { it.isInt() }
            ?.toInt()
        return configured?.takeIf { it > 0 } ?: DEFAULT_MAX_LOG_LENGTH
    }

    private companion object {
        const val DEFAULT_MAX_LOG_LENGTH = 2000
    }
}
