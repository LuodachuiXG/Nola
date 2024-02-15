package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Tag
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.TagDao
import cc.loac.data.sql.tables.Tags
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 标签表操作接口实现类
 */
class TagDaoImpl : TagDao {
    /**
     * 将数据库检索结果转为 [Tag] 实体类
     */
    private fun resultRowToTag(row: ResultRow) = Tag(
        tagId = row[Tags.tagId],
        displayName = row[Tags.displayName],
        slug = row[Tags.slug]
    )

    /**
     * 添加标签
     * @param tag 标签数据类
     */
    override suspend fun addTag(tag: Tag): Tag? = dbQuery {
        val insertStatement = Tags.insert {
            it[displayName] = tag.displayName
            it[slug] = tag.slug
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToTag)
    }

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    override suspend fun deleteTag(tagIds: List<Int>): Boolean = dbQuery {
        Tags.deleteWhere {
            tagId inList tagIds
        } > 0
    }
    /**
     * 修改标签
     * @param tag 标签数据类
     */
    override suspend fun updateTag(tag: Tag): Boolean = dbQuery {
        Tags.update({
            Tags.tagId eq tag.tagId
        }) {
            it[displayName] = tag.displayName
            it[slug] = tag.slug
        } > 0
    }

    /**
     * 获取所有标签
     */
    override suspend fun tags(): List<Tag> = dbQuery {
        Tags.selectAll().map(::resultRowToTag)
    }

    /**
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    override suspend fun tag(tagId: Int): Tag? = dbQuery {
        Tags
            .selectAll().where { Tags.tagId eq tagId }
            .map(::resultRowToTag)
            .singleOrNull()
    }

    /**
     * 根据标签显示名称获取标签
     * @param displayName 标签显示名称
     */
    override suspend fun tag(displayName: String): Tag? = dbQuery {
        Tags
            .selectAll().where { Tags.displayName eq displayName }
            .map(::resultRowToTag)
            .singleOrNull()
    }
}