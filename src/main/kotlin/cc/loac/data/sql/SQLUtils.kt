package cc.loac.data.sql

import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.utils.error
import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

/**
 * 开启分页功能
 * @param page 当前页
 * @param size 每页条数
 * @param transform 转换方法，将检索结果 ResultRow 转为 T 的方法
 * @param query 检索条件
 * @return 返回 [Pager] 分页数据类
 */
suspend fun <T> FieldSet.startPage(
    page: Int,
    size: Int,
    transform: (ResultRow) -> T,
    query: FieldSet.() -> Query
): Pager<T> {
    // 计算 limit 的偏移量
    val offset = ((page - 1) * size).toLong()
    // 查询给定的 query 检索条件下的总条数
    val totalData = dbQuery { query().count() }
    totalData.toString().error()
    // 检索数据、分页、将检索结果转为 T 类型集合
    val data = dbQuery {
        this.query().limit(size, offset).map(transform)
    }
    // 计算总页数
    val totalPage = if (totalData % size == 0L) totalData / size else totalData / size + 1
    return Pager(page, size, data, totalData, totalPage)
}