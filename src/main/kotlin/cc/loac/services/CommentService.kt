package cc.loac.services

import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager

/**
 * 评论服务接口
 */
interface CommentService {
    /**
     * 添加评论
     * @param comment 评论数据类
     */
    suspend fun addComment(comment: Comment): Comment?

    /**
     * 根据评论 ID 删除评论
     * @param id 评论 ID
     */
    suspend fun deleteCommentById(id: Long): Boolean

    /**
     * 根据评论 ID 数组删除评论
     * @param ids 评论 ID 数组
     */
    suspend fun deleteCommentByIds(ids: List<Long>): Boolean

    /**
     * 根据文章 ID 删除评论
     * @param postId 文章 ID
     */
    suspend fun deleteCommentByPostId(postId: Long): Boolean

    /**
     * 根据父评论 ID 数组删除评论（删除的是这些父评论的子评论，父评论不会删除）
     * @param ids 父评论 ID
     */
    suspend fun deleteCommentByParentIds(ids: List<Long>): Boolean

    /**
     * 修改评论
     * 仅可修改以下字段：content、site、displayName、email、isPass
     * @param comment 评论数据类
     */
    suspend fun updateComment(comment: Comment): Boolean

    /**
     * 批量设置评论是否通过审核
     * @param ids 评论 ID 数组
     * @param isPass 是否通过审核
     */
    suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean

    /**
     * 分页获取所有评论
     * @param page 当前页数
     * @param size 每页条数
     * @param postId 文章 ID
     * @param commentId 评论 ID
     * @param parentId 父评论 ID
     * @param email 评论者邮箱
     * @param displayName 评论者昵称
     * @param isPass 是否通过审核
     * @param key 关键字
     * @param sort 排序方式（默认时间降序）
     * @param tree 是否将子评论放置到父评论的 children 字段中（默认 false）
     *             此项为 true 时，commentId、parentId、email、displayName、isPass、key 参数无效
     */
    suspend fun comments(
        page: Int,
        size: Int,
        postId: Long? = null,
        commentId: Long? = null,
        parentId: Long? = null,
        email: String? = null,
        displayName: String? = null,
        isPass: Boolean? = null,
        key: String? = null,
        sort: CommentSort? = null,
        tree: Boolean = false
    ): Pager<Comment>

    /**
     * 根据评论 ID 获取评论
     * @param id 评论 ID
     */
    suspend fun commentById(id: Long): Comment?
}