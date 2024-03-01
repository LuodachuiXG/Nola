package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostSort.*
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
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
        autoGenerateExcerpt = row[Posts.autoGenerateExcerpt],
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
            it[autoGenerateExcerpt] = pr.autoGenerateExcerpt
            it[excerpt] = pr.excerpt ?: ""
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
            it[autoGenerateExcerpt] = pr.autoGenerateExcerpt
            it[excerpt] = pr.excerpt ?: ""
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
     * 修改文章摘要
     * @param postId 文章 ID
     * @param excerpt 摘要
     */
    override suspend fun updatePostExcerpt(postId: Int, excerpt: String): Boolean = dbQuery {
        Posts.update({
            Posts.postId eq postId
        }) {
            it[Posts.excerpt] = excerpt
        } > 0
    }

    /**
     * 获取所有文章
     */
    override suspend fun posts(): List<Post> = dbQuery {
        val posts = Posts
            .selectAll()
            .orderBy(Posts.createTime, SortOrder.DESC)
            .map(::resultRowToPost)
        getPostTagAndCategory(posts)
        posts
    }

    /**
     * 根据文章 ID 获取文章
     * @param postIds 文章 ID 集合
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    override suspend fun posts(postIds: List<Int>, includeTagAndCategory: Boolean): List<Post> = dbQuery {
        val posts = Posts
            .selectAll()
            .where { Posts.postId inList postIds }
            .orderBy(Posts.createTime, SortOrder.DESC)
            .map(::resultRowToPost)
        if (includeTagAndCategory) getPostTagAndCategory(posts)
        posts
    }

    /**
     * 分页获取所有文章
     * @param page 当前页数
     * @param size 每页条数
     * @param status 文章状态
     * @param visible 文章可见性
     * @param key 关键字
     * @param tag 文章标签
     * @param category 文章分类
     * @param sort 文章排序
     */
    override suspend fun posts(
        page: Int,
        size: Int,
        status: PostStatus?,
        visible: PostVisible?,
        key: String?,
        tag: Int?,
        category: Int?,
        sort: PostSort?
    ): Pager<Post> {
        // 基础查询条件
        val query =
            Posts.join(PostContents, JoinType.LEFT, additionalConstraint = { Posts.postId eq PostContents.postId })
                .selectAll()
        // 如果文章状态不为空，则添加状态查询条件
        if (status != null) query.andWhere { Posts.status eq status }
        // 如果文章可见性不为空，则添加可见性查询条件
        if (visible != null) query.andWhere { Posts.visible eq visible }
        // 如果关键字不为空，则添加关键字查询条件
        if (key != null) query.andWhere { sqlQueryKey(key) }
        // 如果文章标签不为空，则添加标签查询条件
        if (tag != null) {
            // 与当前标签匹配的的文章 ID 集合
            val matchedPostIds = dbQuery {
                PostTags.selectAll().where {
                    PostTags.tagId eq tag
                }.map { it[PostTags.postId] }
            }
            query.andWhere {
                Posts.postId inList matchedPostIds
            }
        }
        // 如果文章分类不为空，则添加分类查询条件
        if (category != null) {
            // 与当前分类匹配的的文章 ID 集合
            val matchedPostIds = dbQuery {
                PostCategories.selectAll().where {
                    PostCategories.categoryId eq category
                }.map { it[PostCategories.postId] }
            }
            query.andWhere {
                Posts.postId inList matchedPostIds
            }
        }

        when(sort) {
            CREATE_DESC -> query.orderBy(Posts.createTime, SortOrder.DESC)
            CREATE_ASC -> query.orderBy(Posts.createTime, SortOrder.ASC)
            MODIFY_DESC -> query.orderBy(Posts.lastModifyTime, SortOrder.DESC)
            MODIFY_ASC -> query.orderBy(Posts.lastModifyTime, SortOrder.ASC)
            VISIT_DESC -> query.orderBy(Posts.visit, SortOrder.DESC)
            VISIT_ASC -> query.orderBy(Posts.visit, SortOrder.ASC)
            null -> {}
        }


        val pager = Posts.startPage(page, size, ::resultRowToPost) { query }
        getPostTagAndCategory(pager.data)
        return pager
    }

    /**
     * 根据文章别名获取文章
     * @param slug 文章别名
     */
    override suspend fun postBySlug(slug: String): Post? = dbQuery {
        Posts
            .selectAll()
            .where { Posts.slug eq slug }
            .map(::resultRowToPost)
            .firstOrNull()
    }

    /**
     * 根据关键字获取文章
     * 关键字：文章标题、别名、摘要、内容
     * @param key 关键字
     */
    override suspend fun postsByKey(key: String): List<Post> = dbQuery {
        Posts
            .join(PostContents, JoinType.LEFT, additionalConstraint = { Posts.postId eq PostContents.postId })
            .selectAll()
            .where {
                // 文章关键字查询 SQL 语句
                sqlQueryKey(key)
            }
            .orderBy(Posts.createTime, SortOrder.DESC)
            .map(::resultRowToPost)
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
                            PostContents.status eq
                                    (PostContentStatus.DRAFT) and
                                    (PostContents.draftName eq draftName)
                        } else {
                            PostContents.status eq PostContentStatus.PUBLISHED
                        }
            }.map(::resultRowToPostContent).firstOrNull()
    }

    /**
     * 修改文章内容
     * @param postContent 文章内容请求数据类
     */
    override suspend fun updatePostContent(
        postContent: PostContentRequest,
        status: PostContentStatus,
        draftName: String?
    ): Boolean = dbQuery {
        PostContents.update({
            PostContents.postId eq postContent.postId and
                    (PostContents.status eq status) andIfNotNull
                    (if (status == PostContentStatus.DRAFT) PostContents.draftName eq draftName else null)
        }) {
            it[content] = postContent.content
        } > 0
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

    /**
     * SQL 语句
     * 文章关键字查询
     * @param key 关键字
     */
    private fun sqlQueryKey(key: String): Op<Boolean> {
        return (Posts.title like "%$key%" or
                (Posts.slug like "%$key%") or
                (Posts.excerpt like "%$key%") or
                (PostContents.content like "%$key%")) and
                (Posts.status eq PostStatus.PUBLISHED)
    }
}