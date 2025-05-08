package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Tag
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.TagDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.PostTags
import cc.loac.data.sql.tables.Tags
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 标签表操作接口实现类
 */
class TagDaoImpl : TagDao {

    /**
     * 将数据库检索结果转为 [Tag] 标签数据类
     * @param includePostCount 是否包含文章数量
     */
    private fun resultRowToTag(row: ResultRow, includePostCount: Boolean = true) = Tag(
        tagId = row[Tags.tagId],
        displayName = row[Tags.displayName],
        slug = row[Tags.slug],
        color = row[Tags.color],
        postCount = if (includePostCount) row[PostTags.postTagId.count()] else 0
    )

    /**
     * 添加标签
     * @param tag 标签数据类
     */
    override suspend fun addTag(tag: Tag): Tag? = dbQuery {
        Tags.insert {
            it[displayName] = tag.displayName
            it[slug] = tag.slug
            it[color] = if (tag.color.isNullOrBlank()) null else tag.color
        }.resultedValues?.singleOrNull()?.let { resultRow ->
            resultRowToTag(resultRow, false)
        }
    }

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    override suspend fun deleteTags(tagIds: List<Long>): Boolean = dbQuery {
        // 先删除与标签对应的文章关联
        PostTags.deleteWhere {
            tagId inList tagIds
        }

        Tags.deleteWhere {
            tagId inList tagIds
        } > 0
    }

    /**
     * 根据别名删除标签
     * @param slugs 标签别名集合
     */
    override suspend fun deleteTagsBySlugs(slugs: List<String>): Boolean = dbQuery {
        Tags.deleteWhere {
            slug inList slugs
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
            it[color] = if (tag.color.isNullOrBlank()) null else tag.color
        } > 0
    }

    /**
     * 获取所有标签
     */
    override suspend fun tags(): List<Tag> = dbQuery {
        sqlSelectTag().map(::resultRowToTag)
    }

    /**
     * 分页获取所有标签
     * @param page 当前页
     * @param size 每页条数
     */
    override suspend fun tags(page: Int, size: Int): Pager<Tag> {
        return Tags.startPage(page, size, ::resultRowToTag) {
            // 获取标签和对应的文章数量
            sqlSelectTag()
        }
    }

    /**
     * 根据文章 ID 获取标签
     */
    override suspend fun tags(postId: Long): List<Tag> = dbQuery {
        sqlSelectTag()
            .where { PostTags.postId eq postId }
            .map(::resultRowToTag)
    }


    /**
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    override suspend fun tag(tagId: Long): Tag? = dbQuery {
        sqlSelectTag()
            .where { Tags.tagId eq tagId }
            .map(::resultRowToTag)
            .singleOrNull()
    }

    /**
     * 根据标签 ID 集合获取标签
     * @param tagIds 标签 ID 集合
     */
    override suspend fun tags(tagIds: List<Long>): List<Tag> = dbQuery {
        sqlSelectTag()
            .where { Tags.tagId inList tagIds }
            .map(::resultRowToTag)
    }

    /**
     * 根据标签名称获取标签
     * @param displayName 标签名称
     */
    override suspend fun tag(displayName: String): Tag? = dbQuery {
        sqlSelectTag()
            .where { Tags.displayName eq displayName }
            .map(::resultRowToTag)
            .singleOrNull()
    }

    /**
     * 根据标签别名获取标签
     * @param slug 标签别名
     */
    override suspend fun tagBySlug(slug: String): Tag? = dbQuery {
        sqlSelectTag()
            .where { Tags.slug eq slug }
            .map(::resultRowToTag)
            .singleOrNull()
    }

    /**
     * 标签数量
     */
    override suspend fun tagCount(): Long = dbQuery {
        Tags.selectAll().count()
    }

    /**
     * SQL 语句
     * 获取标签和对应的文章数量
     */
    private fun sqlSelectTag(): Query {
        return Tags.leftJoin(PostTags, additionalConstraint = { Tags.tagId eq PostTags.tagId })
            .select(
                Tags.tagId, Tags.displayName,
                Tags.slug, Tags.color, PostTags.postTagId.count()
            )
            .groupBy(Tags.tagId)
            .orderBy(Tags.tagId, SortOrder.DESC)
    }
}