package cc.loac.services.impl

import cc.loac.data.models.Operation
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.OperationDao
import cc.loac.services.OperationService
import org.koin.java.KoinJavaComponent.inject

/**
 * 操作记录服务接口实现类
 */
class OperationServiceImpl : OperationService {

    private val operationDao: OperationDao by inject(OperationDao::class.java)

    /**
     * 添加操作记录
     * @param operation 操作记录数据类
     */
    override suspend fun addOperation(operation: Operation): Operation? {
        return operationDao.addOperation(operation)
    }

    /**
     * 删除操作记录
     * @param operationIds 操作记录 ID 列表
     */
    override suspend fun deleteOperation(operationIds: List<Long>): Boolean {
        return operationDao.deleteOperation(operationIds)
    }

    /**
     * 删除所有操作记录
     */
    override suspend fun deleteAllOperations(): Boolean {
        return operationDao.deleteAllOperations()
    }

    /**
     * 删除指定时间戳之前的操作记录
     * @param time 时间戳（毫秒）
     */
    override suspend fun deleteBefore(time: Long): Boolean {
        return operationDao.deleteBefore(time)
    }

    /**
     * 获取所有操作记录
     */
    override suspend fun operations(): List<Operation> {
        return operationDao.operations()
    }

    /**
     * 获取操作记录
     * @param page 当前页
     * @param size 每页条数
     * @param sort 排序
     */
    override suspend fun operations(page: Int, size: Int, sort: CommentSort?): Pager<Operation> {
        if (page == 0) {
            // 获取所有操作记录
            val operations = operations()
            return Pager(0, 0, operations, operations.size.toLong(), 1)
        }
        return operationDao.operations(page, size, sort)
    }
}