package cc.loac.data.sql.dao.impl

import cc.loac.data.models.AccessLog
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.AccessLogDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.AccessLogs
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 访问日志操作接口实现类
 */
class AccessLogDaoImpl : AccessLogDao {

    /**
     * 将数据库检索结果转为 [AccessLog] 日志数据类
     */
    private fun resultRowToAccessLog(row: ResultRow) = AccessLog(
        accessLogId = row[AccessLogs.accessLogId],
        path = row[AccessLogs.path],
        ip = row[AccessLogs.ip],
        accessTime = row[AccessLogs.accessTime],
        type = row[AccessLogs.type],
        message = row[AccessLogs.message]
    )

    /**
     * 增加访问日志
     * @param accessLog 访问日志数据类
     */
    override suspend fun addAccessLog(accessLog: AccessLog): AccessLog? = dbQuery {
        val insertStatement = AccessLogs.insert {
            it[path] = accessLog.path
            it[ip] = accessLog.ip
            it[accessTime] = accessLog.accessTime
            it[type] = accessLog.type
            it[message] = accessLog.message
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRow ->
            resultRowToAccessLog(resultRow)
        }
    }

    /**
     * 删除访问日志
     * @param accessLogId 访问日志 ID
     */
    override suspend fun deleteLog(accessLogId: Int): Boolean = dbQuery {
        AccessLogs.deleteWhere {
            AccessLogs.accessLogId eq accessLogId
        } > 0
    }

    /**
     * 删除最早的指定数量的日志
     * @param count 要删除的日志数量
     */
    override suspend fun deleteEarliestLogs(count: Int): Boolean = dbQuery {
        AccessLogs.deleteWhere {
            accessLogId inList AccessLogs
                .selectAll()
                .orderBy(accessTime)
                .limit(count)
                .map {
                    it[accessLogId]
                }
        } > 0
    }

    /**
     * 获取访问日志
     * @param accessLogId 访问日志 ID
     */
    override suspend fun accessLog(accessLogId: Int): AccessLog? = dbQuery {
        AccessLogs
            .selectAll()
            .where { AccessLogs.accessLogId eq accessLogId }
            .map(::resultRowToAccessLog)
            .firstOrNull()
    }

    /**
     * 分页获取所有访问日志
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun accessLogs(page: Int, size: Int): Pager<AccessLog> {
        return AccessLogs.startPage(page, size, ::resultRowToAccessLog) {
            selectAll()
        }
    }

    /**
     * 获取日志总长度
     */
    override suspend fun count(): Long = dbQuery {
        AccessLogs.selectAll().count()
    }
}