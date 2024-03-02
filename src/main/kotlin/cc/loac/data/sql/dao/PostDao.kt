package cc.loac.data.sql.dao

import cc.loac.data.models.Category
import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.Tag
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.ApiPostResponse
import cc.loac.data.responses.Pager
import cc.loac.data.responses.PostContentResponse

/**
 * 文章操作接口
 */
interface PostDao {

    /**
     * 添加文章
     * @param pr 添加文章请求数据类
     */
    suspend fun addPost(pr: PostRequest): Post?

    /**
     * 删除文章
     * @param postIds 文章 ID 集合
     */
    suspend fun deletePosts(postIds: List<Int>): Boolean

    /**
     * 修改文章为删除状态
     * @param postIds 文章 ID 集合
     */
    suspend fun updatePostStatusToDeleted(postIds: List<Int>): Boolean

    /**
     * 修改文章
     * @param pr 文章请求数据类
     */
    suspend fun updatePost(pr: PostRequest): Boolean

    /**
     * 修改文章摘要
     * @param postId 文章 ID
     * @param excerpt 摘要
     */
    suspend fun updatePostExcerpt(postId: Int, excerpt: String): Boolean

    /**
     * 修改文章最后修改时间
     * @param postId 文章 ID
     * @param time 最后修改时间
     */
    suspend fun updatePostLastModifyTime(postId: Int, time: Long? = null): Boolean

    /**
     * 根据文章 ID 获取文章分类
     * @param postId 文章 ID
     */
    suspend fun categoryByPostId(postId: Int): Category?

    /**
     * 根据文章 ID 获取文章标签
     * @param postId 文章 ID
     */
    suspend fun tagsByPostId(postId: Int): List<Tag>

    /**
     * 获取所有文章
     */
    suspend fun posts(): List<Post>

    /**
     * 根据文章 ID 获取文章
     * @param postIds 文章 ID 集合
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    suspend fun posts(postIds: List<Int>, includeTagAndCategory: Boolean): List<Post>

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
    suspend fun posts(
        page: Int,
        size: Int,
        status: PostStatus? = null,
        visible: PostVisible? = null,
        key: String? = null,
        tag: Int? = null,
        category: Int? = null,
        sort: PostSort? = null
    ): Pager<Post>

    /**
     * 根据文章别名获取文章
     * @param slug 文章别名
     */
    suspend fun postBySlug(slug: String): Post?

    /**
     * 根据关键字获取文章
     * 关键字：文章标题、别名、摘要、内容
     * @param key 关键字
     */
    suspend fun postsByKey(key: String): List<Post>

    /**
     * 获取文章 API 接口
     * @param page 当前页数
     * @param size 每页条数
     * @param key 关键字
     * @param tag 文章标签
     * @param category 文章分类
     */
    suspend fun apiPosts(
        page: Int,
        size: Int,
        key: String?, tag: Int?, category: Int?
    ): Pager<ApiPostResponse>

    /**
     * 获取文章所有内容
     * 包括文章正文和文章所有草稿
     * @param postId 文章 ID
     */
    suspend fun postContents(postId: Int): List<PostContentResponse>

    /**
     * 获取文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftName 草稿名
     */
    suspend fun postContent(postId: Int, status: PostContentStatus, draftName: String?): PostContent?

    /**
     * 添加文章草稿
     * @param postId 文章 ID
     * @param content 文章内容
     * @param draftName 草稿名
     */
    suspend fun addPostDraft(postId: Int, content: String, draftName: String): PostContent?

    /**
     * 删除文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftNames 草稿名集合
     */
    suspend fun deletePostContent(postId: Int, status: PostContentStatus, draftNames: List<String>?): Boolean

    /**
     * 修改文章内容
     * @param postContent 文章内容请求数据类
     */
    suspend fun updatePostContent(
        postContent: PostContentRequest,
        status: PostContentStatus,
        draftName: String?
    ): Boolean

    /**
     * 修改文章草稿名
     * @param postId 文章 ID
     * @param oldName 老草稿名
     * @param newName 新草稿名
     */
    suspend fun updatePostDraftName(postId: Int, oldName: String, newName: String): Boolean

    /**
     * 将文章草稿转换为文章正文
     * @param postId 文章 ID
     * @param draftName 草稿名
     * @param deleteContent 是否删除原来的正文
     * @param contentName 文章正文名，留空将默认使用被转换为正文的旧草稿名。
     */
    suspend fun updatePostDraft2Content(
        postId: Int,
        draftName: String,
        deleteContent: Boolean,
        contentName: String?
    ): Boolean


}