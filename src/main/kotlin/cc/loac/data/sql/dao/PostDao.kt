package cc.loac.data.sql.dao

import cc.loac.data.models.Post
import cc.loac.data.requests.AddPostRequest
import cc.loac.data.responses.Pager

/**
 * 文章操作接口
 */
interface PostDao {

    /**
     * 添加文章
     * @param pr 添加文章请求数据类
     */
    suspend fun addPost(pr: AddPostRequest): Post?

    /**
     * 删除文章
     * @param postIds 文章 ID 集合
     */
    suspend fun deletePosts(postIds: List<Int>): Boolean

    /**
     * 修改文章
     * @param post 文章数据类
     */
    suspend fun updatePost(post: Post): Boolean

    /**
     * 获取所有文章
     */
    suspend fun posts(): List<Post>

    /**
     * 分页获取所有文章
     * @param page 页数
     * @param size 每页条数
     */
    suspend fun posts(page: Int, size: Int): Pager<Post>
}