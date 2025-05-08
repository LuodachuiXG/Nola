package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Link
import cc.loac.data.models.enums.LinkSort
import cc.loac.data.requests.LinkRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.LinkDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Links
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.*

/**
 * 友情链接操作接口实现类
 */
class LinkDaoImpl : LinkDao {

    /**
     * 将数据库检索结果转为 [Link] 友情链接数据类
     */
    private fun resultRowToLink(row: ResultRow) = Link(
        linkId = row[Links.linkId],
        displayName = row[Links.displayName],
        url = row[Links.url],
        logo = row[Links.logo],
        description = row[Links.description],
        priority = row[Links.priority],
        remark = row[Links.remark],
        isLost = row[Links.isLost],
        createTime = row[Links.createTime],
        lastModifyTime = row[Links.lastModifyTime]
    )


    /**
     * 添加友情链接
     * @param link 友情链接请求数据类
     */
    override suspend fun addPost(link: LinkRequest): Link? = dbQuery {
        Links.insert {
            it[displayName] = link.displayName
            it[url] = link.url
            it[logo] = if (link.logo.isNullOrBlank()) null else link.logo
            it[description] = if (link.description.isNullOrBlank()) null else link.description
            it[priority] = link.priority
            it[remark] = if (link.remark.isNullOrBlank()) null else link.remark
            it[isLost] = link.isLost
            it[createTime] = Date().time
        }.resultedValues?.singleOrNull()?.let(::resultRowToLink)
    }

    /**
     * 删除友情链接
     * @param ids 友情链接 ID 集合
     */
    override suspend fun deleteLinks(ids: List<Long>): Boolean = dbQuery {
        Links.deleteWhere {
            linkId inList ids
        } > 0
    }

    /**
     * 修改友情链接
     * @param link 友情链接请求数据类
     */
    override suspend fun updateLink(link: LinkRequest): Boolean = dbQuery {
        Links.update({
            Links.linkId eq link.linkId!!
        }) {
            it[displayName] = link.displayName
            it[url] = link.url
            it[logo] = if (link.logo.isNullOrBlank()) null else link.logo
            it[description] = if (link.description.isNullOrBlank()) null else link.description
            it[priority] = link.priority
            it[remark] = if (link.remark.isNullOrBlank()) null else link.remark
            it[isLost] = link.isLost
            it[lastModifyTime] = Date().time
        } > 0
    }

    /**
     * 获取所有友情链接
     * @param sort 友情链接排序
     */
    override suspend fun links(sort: LinkSort?): List<Link> = dbQuery {
        sqlQueryLinks(sort).map(::resultRowToLink)
    }

    /**
     * 分页获取友情链接
     * @param page 当前页
     * @param size 每页条数
     * @param sort 友情链接排序
     */
    override suspend fun links(page: Int, size: Int, sort: LinkSort?): Pager<Link> {
        return Links.startPage(page, size, ::resultRowToLink) {
            sqlQueryLinks(sort)
        }
    }

    /**
     * 获取友情链接数量
     */
    override suspend fun linkCount(): Long = dbQuery {
        Links.selectAll().count()
    }

    /**
     * SQL 语句
     * 查询友情链接
     * @param sort 友情链接排序
     */
    private fun sqlQueryLinks(sort: LinkSort?): Query {
        val query = Links.selectAll()
        when (sort) {
            LinkSort.PRIORITY_DESC -> query.orderBy(Links.priority, SortOrder.DESC)
            LinkSort.PRIORITY_ASC -> query.orderBy(Links.priority, SortOrder.ASC)
            LinkSort.CREATE_TIME_DESC -> query.orderBy(Links.createTime, SortOrder.DESC)
            LinkSort.CREATE_TIME_ASC -> query.orderBy(Links.createTime, SortOrder.ASC)
            LinkSort.MODIFY_TIME_DESC -> query.orderBy(Links.lastModifyTime, SortOrder.DESC)
            LinkSort.MODIFY_TIME_ASC -> query.orderBy(Links.lastModifyTime, SortOrder.ASC)
            // 默认优先级降序排列
            else -> query.orderBy(Links.priority, SortOrder.DESC)
        }
        return query
    }
}