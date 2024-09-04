package cc.loac.data.sql.dao

import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager

/**
 * 评论操作接口
 */
interface CommentDao {

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
     * 根据父评论 ID 删除评论
     * @param parentId 父评论 ID
     */
    suspend fun deleteCommentByParentId(parentId: Long): Boolean

    /**
     * 修改评论
     * @param comment 评论数据类
     */
    suspend fun updateComment(comment: Comment): Boolean

    /**
     * 批量设置评论是否通过审核
     * @param ids 评论 ID 数组
     */
    suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean

    /**
     * 分页获取所有评论
     * @param postId 文章 ID
     * @param parentId 父评论 ID
     * @param email 评论者邮箱
     * @param displayName 评论者昵称
     * @param isPass 是否通过审核
     * @param sort 排序方式
     * @param page 当前页数
     * @param size 每页条数
     */
    suspend fun comments(
        postId: Long? = null,
        parentId: Long? = null,
        email: String? = null,
        displayName: String? = null,
        isPass: Boolean? = null,
        sort: CommentSort = CommentSort.CREATE_TIME_DESC,
        page: Int,
        size: Int
    ): Pager<Comment>


    /**
     * 根据评论 ID 获取评论
     * @param id 评论 ID
     */
    suspend fun commentById(id: Long): Comment?

}