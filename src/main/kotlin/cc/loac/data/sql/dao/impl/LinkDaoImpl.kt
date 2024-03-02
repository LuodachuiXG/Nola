package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Link
import cc.loac.data.requests.LinkRequest
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.LinkDao
import cc.loac.data.sql.tables.Links
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
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
        createTime = row[Links.createTime]
    )


    /**
     * 添加友情链接
     * @param link 友情链接请求数据类
     */
    override suspend fun addPost(link: LinkRequest): Link? = dbQuery {
        Links.insert {
            it[displayName] = link.displayName
            it[url] = link.url
            it[logo] = link.logo
            it[description] = link.description
            it[priority] = link.priority
            it[createTime] = Date().time
        }.resultedValues?.singleOrNull()?.let(::resultRowToLink)
    }

    /**
     * 删除友情链接
     * @param ids 友情链接 ID 集合
     */
    override suspend fun deleteLinks(ids: List<Int>): Boolean = dbQuery {
        Links.deleteWhere {
            linkId inList  ids
        } > 0
    }
}