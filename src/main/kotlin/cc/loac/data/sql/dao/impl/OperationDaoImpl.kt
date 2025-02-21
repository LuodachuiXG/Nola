package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Operation
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.OperationDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Operations
import cc.loac.extensions.toJSON
import cc.loac.utils.info
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less


/**
 * 操作记录接口实现类
 */
class OperationDaoImpl : OperationDao {

    /**
     * 将数据库检索结果转为 [Operation] 操作记录数据类
     */
    private fun resultRowToOperation(row: ResultRow) = Operation(
        operationId = row[Operations.operationId],
        userId = row[Operations.userId],
        username = row[Operations.username],
        operationType = row[Operations.operationType],
        operationDesc = row[Operations.operationDesc],
        createTime = row[Operations.createTime],
    )

    /**
     * 添加操作记录
     * @param operation 操作记录数据类
     */
    override suspend fun addOperation(operation: Operation): Operation? = dbQuery {
        val insertStatement = Operations.insert {
            it[userId] = operation.userId
            it[username] = operation.username
            it[operationType] = operation.operationType
            it[operationDesc] = operation.operationDesc
            it[createTime] = operation.createTime
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRow ->
            resultRowToOperation(resultRow)
        }
    }

    /**
     * 删除操作记录
     * @param operationIds 操作记录 ID 列表
     */
    override suspend fun deleteOperation(operationIds: List<Long>): Boolean = dbQuery {
        Operations.deleteWhere {
            operationId inList operationIds
        } > 0
    }

    /**
     * 删除所有操作记录
     */
    override suspend fun deleteAllOperations(): Boolean = dbQuery {
        Operations.deleteAll() > 0
    }

    /**
     * 删除指定时间戳之前的操作记录
     * @param time 时间戳（毫秒）
     */
    override suspend fun deleteBefore(time: Long): Boolean = dbQuery {
        Operations.deleteWhere {
            createTime less time
        } > 0
    }

    /**
     * 获取所有操作记录
     */
    override suspend fun operations(): List<Operation> = dbQuery {
        Operations.selectAll().map(::resultRowToOperation)
    }

    /**
     * 获取操作记录
     * @param page 当前页
     * @param size 每页条数
     * @param sort 排序
     */
    override suspend fun operations(page: Int, size: Int, sort: CommentSort?): Pager<Operation> {
        return Operations.startPage(page, size, ::resultRowToOperation) {
            sqlQueryOperations(sort)
        }
    }

    /**
     * SQL 语句
     * 查询操作记录
     * @param sort 操作记录排序
     */
    private fun sqlQueryOperations(sort: CommentSort?): Query {
        val query = Operations.selectAll()
        when (sort) {
            CommentSort.CREATE_TIME_ASC -> query.orderBy(Operations.createTime, SortOrder.ASC)
            // 默认创建时间降序
            else -> query.orderBy(Operations.createTime, SortOrder.DESC)
        }
        return query
    }
}