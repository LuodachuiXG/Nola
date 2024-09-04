package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Comment
import cc.loac.data.models.Config
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.CommentDao
import cc.loac.data.sql.tables.Comments
import org.jetbrains.exposed.sql.ResultRow

/**
 * 评论操作接口实现类
 */
class CommentDaoImpl: CommentDao {

    /**
     * 将数据库检索结果转为 [Comment] 评论数据类
     */
    private fun resultRowToComment(row: ResultRow) = Comment(
        commentId = row[Comments.commentId],
        postId = row[Comments.postId],
        parentCommentId = row[Comments.parentCommentId],
        replayCommentId = row[Comments.replyCommentId],
        replayDisplayName = row[Comments.replyDisplayName],
        content = row[Comments.content],
        site = row[Comments.site],
        displayName = row[Comments.displayName],
        email = row[Comments.email],
        createTime = row[Comments.createTime],
        isPass = row[Comments.isPass]
    )

    override suspend fun addComment(comment: Comment): Comment? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCommentById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCommentByIds(ids: List<Long>): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCommentByPostId(postId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCommentByParentId(parentId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun updateComment(comment: Comment): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun comments(
        postId: Long?,
        parentId: Long?,
        email: String?,
        displayName: String?,
        isPass: Boolean?,
        sort: CommentSort,
        page: Int,
        size: Int
    ): Pager<Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun commentById(id: Long): Comment? {
        TODO("Not yet implemented")
    }
}