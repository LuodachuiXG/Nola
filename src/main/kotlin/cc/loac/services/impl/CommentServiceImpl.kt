package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.CommentDao
import cc.loac.data.sql.dao.PostDao
import cc.loac.services.CommentService
import cc.loac.utils.isEmail
import cc.loac.utils.isUrl
import org.koin.java.KoinJavaComponent.inject
import java.util.*

/**
 * 评论服务接口实现类
 */
class CommentServiceImpl: CommentService {
    private val commentDao: CommentDao by inject(CommentDao::class.java)
    private val postDao: PostDao by inject(PostDao::class.java)

    /**
     * 添加评论
     * @param comment 评论数据类
     */
    override suspend fun addComment(comment: Comment): Comment? {
        // 检查评论对应的文章是否存在
        postDao.posts(listOf(comment.postId), false).let {
            if (it.isEmpty()) throw MyException("文章 [${comment.postId}] 不存在")
        }

        // 检查父评论是否存在
        comment.parentCommentId?.let {
            commentById(it) ?: throw MyException("父评论 [${it}] 不存在")
        }

        // 回复的评论是否存在
        comment.replayCommentId?.let {
            commentById(it) ?: throw MyException("回复的评论 [${it}] 不存在")
        }

        // 评论内容为空
        if (comment.content.isBlank()) throw MyException("评论内容不能为空")

        // 站点不合法
        comment.site?.let {
            if (!it.isUrl()) throw MyException("站点地址不合法")
        }

        // 邮箱不合法
        comment.email?.let {
            if (!it.isEmail()) throw MyException("邮箱地址不合法")
        }

        return commentDao.addComment(comment)
    }

    /**
     * 根据评论 ID 删除评论
     * @param id 评论 ID
     */
    override suspend fun deleteCommentById(id: Long): Boolean {
        // 先获取要删除的评论
        val toBeDeleted = commentById(id) ?: return false
        val result = commentDao.deleteCommentById(id)
        // 如果父评论 ID 为空，那当前评论是顶层评论，那就有可能有子评论，同步删除子评论
        if (result && toBeDeleted.parentCommentId == null) {
            return deleteCommentByParentIds(listOf(toBeDeleted.commentId))
        }
        return result
    }

    /**
     * 根据评论 ID 数组删除评论
     * @param ids 评论 ID 数组
     */
    override suspend fun deleteCommentByIds(ids: List<Long>): Boolean {
        // 先尝试获取所有评论
        val comments = commentDao.comments(ids)
        // 删除所有评论
        val result = commentDao.deleteCommentByIds(comments.map { it.commentId })
        // 删除上面被删除的评论的可能存在的子评论
        if (result) {
            // 父评论 ID 为空的评论是顶层评论，可能有子评论
            val toBeDeleted = comments.filter { it.parentCommentId == null }
            deleteCommentByParentIds(toBeDeleted.map{ it.commentId })
        }
        return result
    }

    /**
     * 根据文章 ID 删除评论
     * @param postId 文章 ID
     */
    override suspend fun deleteCommentByPostId(postId: Long): Boolean {
        return commentDao.deleteCommentByPostId(postId)
    }

    /**
     * 根据父评论 ID 数组删除评论（删除的是这些父评论的子评论，父评论不会删除）
     * @param ids 父评论 ID
     */
    override suspend fun deleteCommentByParentIds(ids: List<Long>): Boolean {
        return commentDao.deleteCommentByParentIds(ids)
    }

    /**
     * 修改评论
     * 仅可修改以下字段：content、site、displayName、email、isPass
     * @param comment 评论数据类
     */
    override suspend fun updateComment(comment: Comment): Boolean {
        return commentDao.updateComment(comment)
    }

    /**
     * 批量设置评论是否通过审核
     * @param ids 评论 ID 数组
     */
    override suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean {
        return commentDao.setCommentPass(ids, isPass)
    }

    /**
     * 分页获取所有评论
     * @param postId 文章 ID
     * @param parentId 父评论 ID
     * @param email 评论者邮箱
     * @param displayName 评论者昵称
     * @param isPass 是否通过审核
     * @param sort 排序方式（默认时间降序）
     * @param tree 是否将子评论放置到父评论的 children 字段中（默认 false）
     *             此项为 true 时，parentId、email、displayName、isPass 参数无效
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun comments(
        postId: Long?,
        parentId: Long?,
        email: String?,
        displayName: String?,
        isPass: Boolean?,
        sort: CommentSort?,
        tree: Boolean,
        page: Int,
        size: Int
    ): Pager<Comment> {
        if (tree) {
            // 需要把子评论放到父评论的 children 字段中
            val pager = commentDao.comments(postId, null, null, null, null, sort, page, size)
            val idToComment = pager.data.associateBy { it.commentId }
            // pager.data 是所有平铺的评论 List，parentCommentId 为 null 的评论认为是顶层评论（父评论）
            // 将子评论放到对应的父评论的 children 字段中
            pager.data.forEach { comment ->
                comment.parentCommentId?.let {
                  // 当前评论的父评论 ID 不为空，获得父评论
                    val parent = idToComment[it] ?: return@let
                    if (parent.children == null) parent.children = LinkedList()
                    parent.children!!.add(comment)
                }
            }

            return pager.copy(
                // 此时 pager.data 中的所有父评论的 children 中都已经填充了子评论，所以这里直接过滤掉子评论
                data = pager.data.filter { it.parentCommentId == null }
            )
        } else {
            return commentDao.comments(postId, parentId, email, displayName, isPass, sort, page, size)
        }
    }

    /**
     * 根据评论 ID 获取评论
     * @param id 评论 ID
     */
    override suspend fun commentById(id: Long): Comment? {
        return commentDao.commentById(id)
    }
}