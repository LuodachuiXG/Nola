package cc.loac.services

import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.Pager

/**
 * 文章服务接口
 */
interface PostService {

    /**
     * 添加文章
     * @param pr 文章请求数据类
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
     * 尝试通过文章正文修改文章摘要
     * @param postId 文章 ID
     */
    suspend fun tryUpdatePostExcerptByPostContent(postId: Int): Boolean

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
     * 获取文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftName 草稿名
     */
    suspend fun postContent(
        postId: Int,
        status: PostContentStatus = PostContentStatus.PUBLISHED,
        draftName: String? = null
    ): PostContent?

    /**
     * 修改文章内容
     * @param postContent 文章内容请求数据类
     */
    suspend fun updatePostContent(
        postContent: PostContentRequest,
        status: PostContentStatus = PostContentStatus.PUBLISHED,
        draftName: String? = null
    ): Boolean
}