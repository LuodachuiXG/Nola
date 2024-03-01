package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.PostDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.*
import cc.loac.utils.sha256Hex
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.Date

/**
 * 文章操作接口实现类
 */
class PostDaoImpl : PostDao {

    /**
     * 将数据库检索结果转为 [Post] 文章数据类
     */
    private fun resultRowToPost(row: ResultRow) = Post(
        postId = row[Posts.postId],
        title = row[Posts.title],
        excerpt = row[Posts.excerpt],
        slug = row[Posts.slug],
        cover = row[Posts.cover],
        allowComment = row[Posts.allowComment],
        pinned = row[Posts.pinned],
        status = row[Posts.status],
        visible = row[Posts.visible],
        encrypted = row[Posts.password] != null,
        password = null,
        category = null,
        tags = emptyList(),
        createTime = row[Posts.createTime],
        lastModifyTime = row[Posts.lastModifyTime]
    )

    /**
     * 将数据库检索结果转为 [PostContent] 文章内容数据类
     */
    private fun resultRowToPostContent(row: ResultRow) = PostContent(
        postContentId = row[PostContents.postContentId],
        postId = row[PostContents.postId],
        content = row[PostContents.content],
        status = row[PostContents.status],
        draftName = row[PostContents.draftName],
        lastModifyTime = row[PostContents.lastModifyTime]
    )


    /**
     * 添加文章
     * @param pr 添加文章请求数据类
     */
    override suspend fun addPost(pr: PostRequest): Post? = dbQuery {
        val currentTime = Date().time
        // 插入 post
        val post = Posts.insert {
            it[title] = pr.title
            it[excerpt] = pr.excerpt!!
            it[slug] = pr.slug
            it[cover] = pr.cover
            it[allowComment] = pr.allowComment
            it[pinned] = pr.pinned
            it[status] = pr.status
            it[visible] = pr.visible
            it[password] = pr.password.sha256Hex()
            it[createTime] = currentTime
            it[lastModifyTime] = null
        }.resultedValues?.firstOrNull()?.let(::resultRowToPost)

        post ?: return@dbQuery null

        if (!pr.tagIds.isNullOrEmpty()) {
            // 插入文章标签
            PostTags.batchInsert(pr.tagIds) { tagId ->
                this[PostTags.postId] = post.postId
                this[PostTags.tagId] = tagId
            }
        }

        if (pr.categoryId != null) {
            // 插入文章分类
            PostCategories.insert {
                it[postId] = post.postId
                it[categoryId] = pr.categoryId
            }
        }

        // 插入文章内容
        PostContents.insert {
            it[postId] = post.postId
            it[content] = pr.content ?: ""
            it[status] = PostContentStatus.PUBLISHED
            it[lastModifyTime] = currentTime
        }

        post
    }

    /**
     * 删除文章
     * @param postIds 文章 ID 集合
     */
    override suspend fun deletePosts(postIds: List<Int>): Boolean = dbQuery {
        // 删除文章内容
        PostContents.deleteWhere { postId inList postIds } > 0
        // 删除文章分类
        PostCategories.deleteWhere { postId inList postIds } > 0
        // 删除文章标签
        PostTags.deleteWhere { postId inList postIds } > 0
        // 删除文章
        Posts.deleteWhere { postId inList postIds } > 0
    }

    /**
     * 修改文章为删除状态
     * @param postIds 文章 ID 集合
     */
    override suspend fun updatePostStatusToDeleted(postIds: List<Int>): Boolean = dbQuery {
        Posts.update({
            Posts.postId inList postIds
        }) {
            it[status] = PostStatus.DELETED
        } > 0
    }

    /**
     * 修改文章
     * @param pr 文章请求数据类
     */
    override suspend fun updatePost(pr: PostRequest): Boolean = dbQuery {
        // 删除文章标签
        PostTags.deleteWhere { postId eq pr.postId!! }
        // 删除文章分类
        PostCategories.deleteWhere { postId eq pr.postId!! }

        // 插入文章标签
        if (!pr.tagIds.isNullOrEmpty()) {
            PostTags.batchInsert(pr.tagIds) { tagId ->
                this[PostTags.postId] = pr.postId!!
                this[PostTags.tagId] = tagId
            }
        }

        // 插入文章分类
        if (pr.categoryId != null) {
            PostCategories.insert {
                it[postId] = pr.postId!!
                it[categoryId] = pr.categoryId
            }
        }

        // 更新文章
        Posts.update({
            Posts.postId eq pr.postId!!
        }) {
            it[title] = pr.title
            it[excerpt] = pr.excerpt!!
            it[slug] = pr.slug
            it[allowComment] = pr.allowComment
            it[status] = pr.status
            it[visible] = pr.visible
            it[cover] = pr.cover
            it[pinned] = pr.pinned
            it[password] = pr.password.sha256Hex()
        } > 0
    }

    /**
     * 获取所有文章
     */
    override suspend fun posts(): List<Post> = dbQuery {
        val posts = Posts.selectAll().map(::resultRowToPost)
        getPostTagAndCategory(posts)
        posts
    }

    /**
     * 根据文章 ID 获取文章
     * @param postIds 文章 ID 集合
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    override suspend fun posts(postIds: List<Int>, includeTagAndCategory: Boolean): List<Post> = dbQuery {
        val posts = Posts.selectAll().where { Posts.postId inList postIds }.map(::resultRowToPost)
        if (includeTagAndCategory) getPostTagAndCategory(posts)
        posts
    }

    /**
     * 分页获取所有文章
     * @param page 页数
     * @param size 每页条数
     */
    override suspend fun posts(page: Int, size: Int): Pager<Post> {
        val pager = Posts.startPage(page, size, ::resultRowToPost) {
            selectAll().orderBy(Posts.createTime, SortOrder.DESC)
        }
        getPostTagAndCategory(pager.data)
        return pager
    }

    /**
     * 获取文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftName 草稿名
     */
    override suspend fun postContent(
        postId: Int,
        status: PostContentStatus,
        draftName: String?
    ): PostContent? = dbQuery {
        PostContents
            .selectAll()
            .where {
                PostContents.postId eq postId and
                        if (status == PostContentStatus.DRAFT) {
                            PostContents.status eq PostContentStatus.DRAFT and
                                    (PostContents.draftName eq draftName)
                        } else {
                            PostContents.status eq PostContentStatus.PUBLISHED
                        }
            }.map(::resultRowToPostContent).firstOrNull()
    }

    /**
     * 给列表中的文章填充分类和标签
     * @param posts 文章列表
     */
    private suspend fun getPostTagAndCategory(posts: List<Post>) = dbQuery {
        posts.forEach { post ->
            // 获取文章标签
            post.tags = Tags.join(PostTags, JoinType.LEFT, additionalConstraint = { Tags.tagId eq PostTags.tagId })
                .selectAll().where { PostTags.postId eq post.postId }
                .map(::resultRowToTag)
            // 获取文章分类
            post.category = Categories.join(
                PostCategories,
                JoinType.LEFT,
                additionalConstraint = { Categories.categoryId eq PostCategories.categoryId })
                .selectAll().where { PostCategories.postId eq post.postId }
                .map(::resultRowToCategory)
                .singleOrNull()
        }
    }
}