package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.CommentDao
import cc.loac.data.sql.dao.PostDao
import cc.loac.extensions.isEmail
import cc.loac.extensions.isUrl
import cc.loac.services.CommentService
import org.koin.java.KoinJavaComponent.inject
import java.util.*

/**
 * 评论服务接口实现类
 */
class CommentServiceImpl : CommentService {
    private val commentDao: CommentDao by inject(CommentDao::class.java)
    private val postDao: PostDao by inject(PostDao::class.java)

    /**
     * 添加评论
     * @param comment 评论数据类
     */
    override suspend fun addComment(comment: Comment): Comment? {
        var newComment = comment
        // 检查评论对应的文章是否存在
        postDao.posts(listOf(newComment.postId), false).let {
            if (it.isEmpty()) throw MyException("文章 [${newComment.postId}] 不存在")
        }

        // 检查父评论是否存在
        newComment.parentCommentId?.let {
            commentById(it) ?: throw MyException("父评论 [${it}] 不存在")
        }

        // 如果 replayCommentId 不为空，但是 parentCommentId 为空，则代表当前评论未指定父评论
        // 如果是回复父评论下的某一个子评论，需要同时指定这两个字段
        newComment.replayCommentId?.let {
            if (newComment.parentCommentId == null) throw MyException("未指定父评论")
            // 回复的评论是否存在
            commentById(it)?.let { c ->
                // 如果要回复的评论的父评论 ID 和新添加的评论的父评论 ID 不同，也任务回复的评论不存在
                if (c.parentCommentId != newComment.parentCommentId) throw MyException("回复的评论 [${it}] 不存在")

                // 设置 replayDisplayName
                newComment = newComment.copy(
                    replayDisplayName = c.displayName
                )
            } ?: throw MyException("回复的评论 [${it}] 不存在")
        }

        // 评论内容为空
        if (newComment.content.isBlank()) throw MyException("评论内容不能为空")

        // 昵称不能为空
        if (newComment.displayName.isBlank()) throw MyException("名称不能为空")

        // 站点不合法
        newComment.site?.let {
            if (!it.isUrl()) throw MyException("站点格式错误")
        }

        // 邮箱不合法
        newComment.email.let {
            if (!it.isEmail()) throw MyException("邮箱格式错误")
        }

        return commentDao.addComment(newComment)
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
            deleteCommentByParentIds(toBeDeleted.map { it.commentId })
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
        if (comment.content.isBlank()) throw MyException("评论内容不能为空")
        comment.site?.let {
            if (!it.isUrl()) throw MyException("站点格式错误")
        }
        if (comment.displayName.isBlank()) throw MyException("名称不能为空")
        comment.email.let {
            if (!it.isEmail()) throw MyException("邮箱格式错误")
        }
        return commentDao.updateComment(comment)
    }

    /**
     * 批量设置评论是否通过审核
     * @param ids 评论 ID 数组
     * @param isPass 是否通过审核
     */
    override suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean {
        return commentDao.setCommentPass(ids, isPass)
    }

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
     *             此项为 true 时，commentId、parentId、email、displayName、key 参数无效
     */
    override suspend fun comments(
        page: Int,
        size: Int,
        postId: Long?,
        commentId: Long?,
        parentId: Long?,
        email: String?,
        displayName: String?,
        isPass: Boolean?,
        key: String?,
        sort: CommentSort?,
        tree: Boolean
    ): Pager<Comment> {
        if (tree) {
            // 需要把子评论放到父评论的 children 字段中
            val pager = commentDao.comments(
                page = page,
                size = size,
                postId = postId,
                commentId = null,
                parentId = null,
                email = null,
                displayName = null,
                isPass = isPass,
                key = null,
                sort = sort
            )
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
            return commentDao.comments(
                page = page,
                size = size,
                postId = postId,
                commentId = commentId,
                parentId = parentId,
                email = email,
                displayName = displayName,
                isPass = isPass,
                key = key,
                sort = sort
            )
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