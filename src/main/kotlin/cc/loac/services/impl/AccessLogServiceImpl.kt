package cc.loac.services.impl

import cc.loac.data.models.AccessLog
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.AccessLogDao
import cc.loac.extensions.isInt
import cc.loac.globalEnvironment
import cc.loac.services.AccessLogService
import org.koin.java.KoinJavaComponent.inject

/**
 * 访问日志服务接口实现类
 */
class AccessLogServiceImpl : AccessLogService {

    private val accessLogDao: AccessLogDao by inject(AccessLogDao::class.java)

    /**
     * 增加访问日志
     * @param accessLog 访问日志数据类
     */
    override suspend fun addAccessLog(accessLog: AccessLog): AccessLog? {
        // 最大日志长度
        val maxLength = globalEnvironment.config.propertyOrNull("accessLog.maxLength").let {
            if (it == null) return@let 2000
            if (!it.getString().isInt()) return@let 2000
            it.getString().toInt()
        }
        val result = accessLogDao.addAccessLog(accessLog)
        // 日志数量大于最大长度时，删除最早日志
        val currentCount = count()
        if (currentCount > maxLength) {
            deleteEarliestLogs(currentCount.toInt() - maxLength)
        }
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
     * 删除最早的指定数量的日志
     * @param count 要删除的日志数量
     */
    override suspend fun deleteEarliestLogs(count: Int): Boolean {
        return accessLogDao.deleteEarliestLogs(count)
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
}