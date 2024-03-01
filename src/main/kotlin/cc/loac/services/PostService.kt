package cc.loac.services

import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
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
     * @param page 页数
     * @param size 每页条数
     */
    suspend fun posts(page: Int, size: Int): Pager<Post>

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
}