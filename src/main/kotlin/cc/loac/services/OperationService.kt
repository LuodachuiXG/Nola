package cc.loac.services

import cc.loac.data.models.Operation
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager

/**
 * 操作记录服务接口
 */
interface OperationService {

    /**
     * 添加操作记录
     * @param operation 操作记录数据类
     */
    suspend fun addOperation(operation: Operation): Operation?


    /**
     * 删除操作记录
     * @param operationIds 操作记录 ID 列表
     */
    suspend fun deleteOperation(operationIds: List<Long>): Boolean

    /**
     * 删除所有操作记录
     */
    suspend fun deleteAllOperations(): Boolean

    /**
     * 删除指定时间戳之前的操作记录
     * @param time 时间戳（毫秒）
     */
    suspend fun deleteBefore(time: Long): Boolean

    /**
     * 获取所有操作记录
     */
    suspend fun operations(): List<Operation>

    /**
     * 获取操作记录
     * @param page 当前页
     * @param size 每页条数
     * @param sort 排序
     */
    suspend fun operations(
        page: Int,
        size: Int,
        sort: CommentSort?
    ): Pager<Operation>

    /**
     * 获取最近的操作记录
     */
    suspend fun lastOperation(): Operation?
}