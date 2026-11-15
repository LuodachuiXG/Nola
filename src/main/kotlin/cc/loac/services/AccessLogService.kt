package cc.loac.services

import cc.loac.data.models.AccessLog
import cc.loac.data.responses.Pager

/**
 * 访问日志服务接口
 */
interface AccessLogService {

    /**
     * 启动异步日志写入任务
     */
    fun startWriter()

    /**
     * 将访问日志放入异步写入队列
     * 队列满时丢弃最早日志，避免阻塞请求
     */
    fun enqueueAccessLog(accessLog: AccessLog)

    /**
     * 增加访问日志
     * @param accessLog 访问日志数据类
     */
    suspend fun addAccessLog(accessLog: AccessLog): AccessLog?

    /**
     * 删除访问日志
     * @param accessLogId 访问日志 ID
     */
    suspend fun deleteLog(accessLogId: Long): Boolean

    /**
     * 批量删除访问日志
     * @param accessLogIds 访问日志 ID 集合
     */
    suspend fun deleteLogs(accessLogIds: List<Long>): Boolean

    /**
     * 删除最早的指定数量的日志
     * @param count 要删除的日志数量
     */
    suspend fun deleteEarliestLogs(count: Int): Boolean

    /**
     * 删除指定时间以前的日志
     * @param time 时间戳（毫秒）
     */
    suspend fun deleteBefore(time: Long): Boolean

    /**
     * 删除全部访问日志
     */
    suspend fun deleteAll(): Boolean

    /**
     * 获取访问日志
     * @param accessLogId 访问日志 ID
     */
    suspend fun accessLog(accessLogId: Long): AccessLog?

    /**
     * 分页获取所有访问日志
     * @param page 当前页数
     * @param size 每页条数
     */
    suspend fun accessLogs(page: Int, size: Int): Pager<AccessLog>

    /**
     * 获取日志总长度
     */
    suspend fun count(): Long
}