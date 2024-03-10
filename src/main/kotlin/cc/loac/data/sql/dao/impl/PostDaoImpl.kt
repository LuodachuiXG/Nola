package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Category
import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.Tag
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostSort.*
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.requests.PostStatusRequest
import cc.loac.data.responses.ApiPostResponse
import cc.loac.data.responses.Pager
import cc.loac.data.responses.PostContentResponse
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.data.sql.dao.PostDao
import cc.loac.data.sql.dao.TagDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.*
import cc.loac.utils.launchCoroutine
import cc.loac.utils.sha256Hex
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.koin.java.KoinJavaComponent.inject
import java.util.Date

/**
 * 文章操作接口实现类
 */
class PostDaoImpl : PostDao {

    private val tagDao: TagDao by inject(TagDao::class.java)
    private val categoryDao: CategoryDao by inject(CategoryDao::class.java)

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
        encrypted = !row[Posts.password].isNullOrEmpty(),
        password = null,
        visit = row[Posts.visit],
        category = null,
        tags = emptyList(),
        createTime = row[Posts.createTime],
        lastModifyTime = row[Posts.lastModifyTime]
    )

    /**
     * 将数据库检索结果转为 [ApiPostResponse] 文章 API 响应类
     */
    private fun resultRowToApiPostResponse(row: ResultRow) = ApiPostResponse(
        postId = row[Posts.postId],
        title = row[Posts.title],
        // 如果文章加密，则不返回摘要
        excerpt = if (!row[Posts.password].isNullOrEmpty()) null else row[Posts.excerpt],
        slug = row[Posts.slug],
        cover = row[Posts.cover],
        allowComment = row[Posts.allowComment],
        pinned = row[Posts.pinned],
        encrypted = !row[Posts.password].isNullOrEmpty(),
        visit = row[Posts.visit],
        category = null,
        tags = emptyList(),
        createTime = row[Posts.createTime],
        // 如果文章加密，则不返回最后修改时间
        lastModifyTime = if (!row[Posts.password].isNullOrEmpty()) null else row[Posts.lastModifyTime],
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
     * 将数据库检索结果转为 [PostContentResponse] 文章内容响应数据类
     */
    private fun resultRowToPostContentResponse(row: ResultRow) = PostContentResponse(
        postContentId = row[PostContents.postContentId],
        postId = row[PostContents.postId],
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
            if (pr.encrypted != null && pr.encrypted == true) {
                it[password] = pr.password.sha256Hex()
            } else if (pr.encrypted == false) {
                it[password] = ""
            }
        } > 0
    }

    /**
     * 修改文章状态
     * 文章状态、可见性、置顶
     * @param postStatusRequest 文章状态请求数据类
     */
    override suspend fun updatePostStatus(postStatusRequest: PostStatusRequest): Boolean = dbQuery {
        Posts.update({
            Posts.postId eq postStatusRequest.postId
        }) {
            if (postStatusRequest.status != null) {
                it[status] = postStatusRequest.status
            }
            if (postStatusRequest.visible != null) {
                it[visible] = postStatusRequest.visible
            }
            if (postStatusRequest.pinned != null) {
                it[pinned] = postStatusRequest.pinned
            }
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
     * 修改文章最后修改时间
     * @param postId 文章 ID
     * @param time 最后修改时间
     */
    override suspend fun updatePostLastModifyTime(postId: Int, time: Long?): Boolean = dbQuery {
        Posts.update({
            Posts.postId eq postId
        }) {
            it[lastModifyTime] = time ?: Date().time
        } > 0
    }

    /**
     * 增加文章访问量
     * @param postId 文章 ID
     */
    override suspend fun addPostVisit(postId: Int): Boolean = dbQuery {
        Posts.update({
            Posts.postId eq postId
        }) {
            it[visit] = visit + 1
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
        // 构造查询语句
        val query = sqlQueryPosts(
            status = status,
            visible = visible,
            key = key,
            tag = tag,
            category = category,
            sort = sort
        ) {
            Posts.leftJoin(PostContents, additionalConstraint = {
                Posts.postId eq PostContents.postId and
                        (PostContents.status eq PostContentStatus.PUBLISHED)
            }).selectAll()
        }

        if (page == 0) {
            // 获取所有文章
            val posts = dbQuery { query.map(::resultRowToPost) }
            getPostTagAndCategory(posts)
            return Pager(0, 0, posts, posts.size.toLong(), 1)
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
        val post = Posts
            .selectAll()
            .where { Posts.slug eq slug }
            .map(::resultRowToPost)
            .firstOrNull() ?: return@dbQuery null
        getPostTagAndCategory(listOf(post))
        post
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
     * 获取文章 API 接口
     * @param page 当前页数
     * @param size 每页条数
     * @param key 关键字
     * @param tag 文章标签
     * @param category 文章分类
     */
    override suspend fun apiPosts(
        page: Int,
        size: Int,
        key: String?,
        tag: Int?,
        category: Int?
    ): Pager<ApiPostResponse> {
        // 构造查询语句
        val query = sqlQueryPosts(
            status = PostStatus.PUBLISHED,
            visible = PostVisible.VISIBLE,
            key = key,
            tag = tag,
            category = category,
            sort = PINNED
        ) {
            // 基础查询条件
            Posts.leftJoin(PostContents, additionalConstraint = {
                Posts.postId eq PostContents.postId and
                        (PostContents.status eq PostContentStatus.PUBLISHED)
            }).selectAll()
        }

        if (page == 0) {
            // 获取所有文章
            val posts = dbQuery { query.map(::resultRowToApiPostResponse) }
            getApiPostTagAndCategory(posts)
            return Pager(0, 0, posts, posts.size.toLong(), 1)
        }

        val pager = Posts.startPage(page, size, ::resultRowToApiPostResponse) { query }
        getApiPostTagAndCategory(pager.data)
        return pager
    }

    /**
     * 获取文章所有内容
     * 包括文章正文和文章所有草稿
     * @param postId 文章 ID
     */
    override suspend fun postContents(postId: Int): List<PostContentResponse> = dbQuery {
        PostContents
            .select(
                PostContents.postContentId,
                PostContents.postId, PostContents.status,
                PostContents.draftName, PostContents.lastModifyTime
            )
            .where {
                PostContents.postId eq postId
            }.map(::resultRowToPostContentResponse)
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
     * 添加文章草稿
     * @param postId 文章 ID
     * @param content 文章内容
     * @param draftName 草稿名
     */
    override suspend fun addPostDraft(postId: Int, content: String, draftName: String): PostContent? = dbQuery {
        PostContents.insert {
            it[PostContents.postId] = postId
            it[PostContents.content] = content
            it[PostContents.draftName] = draftName
            it[status] = PostContentStatus.DRAFT
            it[lastModifyTime] = Date().time
        }.resultedValues?.firstOrNull()?.let(::resultRowToPostContent)
    }

    /**
     * 删除文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftNames 草稿名集合
     */
    override suspend fun deletePostContent(
        postId: Int,
        status: PostContentStatus,
        draftNames: List<String>?
    ): Boolean = dbQuery {
        PostContents.deleteWhere {
            PostContents.postId eq postId and
                    (PostContents.status eq status) andIfNotNull
                    (if (status == PostContentStatus.DRAFT && draftNames != null)
                        draftName inList draftNames else null)
        } > 0
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
        val currentTime = Date().time
        val result = PostContents.update({
            PostContents.postId eq postContent.postId and
                    (PostContents.status eq status) andIfNotNull
                    (if (status == PostContentStatus.DRAFT) PostContents.draftName eq draftName else null)
        }) {
            it[content] = postContent.content
            it[lastModifyTime] = currentTime
        } > 0

        if (result && status == PostContentStatus.PUBLISHED) {
            // 文章内容修改成功，并且修改的是正文内容
            launchCoroutine {
                // 修改文章最后修改时间
                updatePostLastModifyTime(postContent.postId, currentTime)
            }
        }
        result
    }

    /**
     * 修改文章草稿名
     * @param postId 文章 ID
     * @param oldName 老草稿名
     * @param newName 新草稿名
     */
    override suspend fun updatePostDraftName(postId: Int, oldName: String, newName: String): Boolean = dbQuery {
        PostContents.update({
            PostContents.postId eq postId and (PostContents.draftName eq oldName)
        }) {
            it[draftName] = newName
        } > 0
    }

    /**
     * 将文章草稿转换为文章正文
     * @param postId 文章 ID
     * @param draftName 草稿名
     * @param deleteContent 是否删除原来的正文
     * @param contentName 文章正文名，留空将默认使用被转换为正文的旧草稿名。
     */
    override suspend fun updatePostDraft2Content(
        postId: Int,
        draftName: String,
        deleteContent: Boolean,
        contentName: String?
    ): Boolean = dbQuery {
        // 获取原来的文章正文内容 ID
        val postContentId = PostContents
            .select(PostContents.postContentId)
            .where {
                (PostContents.postId eq postId) and
                        (PostContents.status eq PostContentStatus.PUBLISHED)
            }.map {
                it[PostContents.postContentId]
            }.firstOrNull() ?: return@dbQuery false
        // 获取要转换的草稿的内容 ID
        val postContentDraftId = PostContents
            .select(PostContents.postContentId)
            .where {
                (PostContents.postId eq postId) and
                        (PostContents.status eq PostContentStatus.DRAFT) and
                        (PostContents.draftName eq draftName)
            }.map { it[PostContents.postContentId] }.firstOrNull() ?: return@dbQuery false

        if (deleteContent) {
            // 删除原来的正文
            PostContents.deleteWhere {
                PostContents.postContentId eq postContentId
            }
        } else {
            // 不删除原来的正文
            // 修改原来的正文为草稿，并修改草稿名
            PostContents.update({
                PostContents.postContentId eq postContentId
            }) {
                it[status] = PostContentStatus.DRAFT
                it[PostContents.draftName] = if (contentName.isNullOrEmpty()) draftName else contentName
            }
        }

        // 将指定的草稿转为正文
        PostContents.update({
            PostContents.postContentId eq postContentDraftId
        }) {
            it[status] = PostContentStatus.PUBLISHED
            it[PostContents.draftName] = null
        } > 0
    }

    /**
     * 验证文章密码是否正确
     * @param postId 文章 ID
     * @param password 密码
     */
    override suspend fun isPostPasswordValid(postId: Int, password: String): Boolean = dbQuery {
        Posts
            .selectAll()
            .where {
                Posts.postId eq postId and (Posts.password eq password.sha256Hex())
            }
            .map { it[Posts.postId] }
            .firstOrNull() != null
    }

    /**
     * 给列表中的文章填充分类和标签
     * @param posts 文章列表
     */
    private suspend fun getPostTagAndCategory(posts: List<Post>) = dbQuery {
        posts.forEach { post ->
            // 获取文章标签
            post.tags = tagDao.tags(post.postId)
            // 获取文章分类
            post.category = categoryDao.categoryByPostId(post.postId)
        }
    }

    /**
     * 给列表中的博客前端 API 响应文章填充分类和标签
     * @param posts 文章列表
     */
    private suspend fun getApiPostTagAndCategory(posts: List<ApiPostResponse>) = dbQuery {
        posts.forEach { post ->
            // 如果当前是 API 请求，同时文章是加密状态的话就跳过当前文章的标签和分类检索
            if (post.encrypted) {
                post.category = null
                post.tags = emptyList()
            } else {
                // 获取文章标签
                post.tags = tagDao.tags(post.postId)
                // 获取文章分类
                post.category = categoryDao.categoryByPostId(post.postId)
            }
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
                ((PostContents.content like "%$key%") and
                        (PostContents.status eq PostContentStatus.PUBLISHED)))
    }


    /**
     * SQL 语句
     * 文章查询
     * @param status 文章状态
     * @param visible 文章可见性
     * @param key 关键字
     * @param tag 文章标签
     * @param category 文章分类
     * @param sort 文章排序排序
     * @param baseQuery 基础查询
     */
    private suspend fun sqlQueryPosts(
        status: PostStatus?,
        visible: PostVisible?,
        key: String?,
        tag: Int?,
        category: Int?,
        sort: PostSort?,
        baseQuery: () -> Query
    ): Query {
        val query = baseQuery()
        // 如果文章状态不为空，则添加状态查询条件
        if (status != null) {
            query.andWhere { Posts.status eq status }
        } else {
            // 默认不获取删除状态的文章
            query.andWhere { Posts.status neq PostStatus.DELETED }
        }
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

        when (sort) {
            CREATE_DESC -> query.orderBy(Posts.createTime, SortOrder.DESC)
            CREATE_ASC -> query.orderBy(Posts.createTime, SortOrder.ASC)
            MODIFY_DESC -> query.orderBy(Posts.lastModifyTime, SortOrder.DESC)
            MODIFY_ASC -> query.orderBy(Posts.lastModifyTime, SortOrder.ASC)
            VISIT_DESC -> query.orderBy(Posts.visit, SortOrder.DESC)
            VISIT_ASC -> query.orderBy(Posts.visit, SortOrder.ASC)
            PINNED -> query.orderBy(Posts.pinned, SortOrder.DESC)
            null -> query.orderBy(Posts.createTime, SortOrder.DESC)
        }
        // 默认都有按照创建时间降序
        query.orderBy(Posts.createTime, SortOrder.DESC)
        return query
    }
}