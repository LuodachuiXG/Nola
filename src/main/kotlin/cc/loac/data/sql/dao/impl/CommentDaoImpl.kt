package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.CommentDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Comments
import cc.loac.data.sql.tables.Posts
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.Date

/**
 * 评论操作接口实现类
 */
class CommentDaoImpl : CommentDao {

    /**
     * 将数据库检索结果转为 [Comment] 评论数据类
     */
    private fun resultRowToComment(row: ResultRow) = Comment(
        commentId = row[Comments.commentId],
        postId = row[Comments.postId],
        postTitle = if (row.hasValue(Posts.title)) row[Posts.title] else null,
        parentCommentId = row[Comments.parentCommentId],
        replyCommentId = row[Comments.replyCommentId],
        replyDisplayName = row[Comments.replyDisplayName],
        content = row[Comments.content],
        site = row[Comments.site],
        displayName = row[Comments.displayName],
        email = row[Comments.email],
        createTime = row[Comments.createTime],
        isPass = row[Comments.isPass]
    )

    /**
     * 添加评论
     * @param comment 评论数据类
     */
    override suspend fun addComment(comment: Comment): Comment? = dbQuery {
        val result = Comments.insert {
            it[postId] = comment.postId
            it[parentCommentId] = comment.parentCommentId
            it[replyCommentId] = comment.replyCommentId
            it[replyDisplayName] = comment.replyDisplayName
            it[content] = comment.content
            it[site] = comment.site
            it[displayName] = comment.displayName
            it[email] = comment.email
            it[createTime] = Date().time
            it[isPass] = comment.isPass
        }
        result.resultedValues?.singleOrNull()?.let {
            resultRowToComment(it)
        }
    }

    /**
     * 根据评论 ID 删除评论
     * @param id 评论 ID
     */
    override suspend fun deleteCommentById(id: Long): Boolean = dbQuery {
        Comments.deleteWhere {
            commentId eq id
        } > 0
    }

    /**
     * 根据评论 ID 数组删除评论
     * @param ids 评论 ID 数组
     */
    override suspend fun deleteCommentByIds(ids: List<Long>): Boolean = dbQuery {
        Comments.deleteWhere {
            commentId inList ids
        } > 0
    }

    /**
     * 根据文章 ID 删除评论
     * @param postId 文章 ID
     */
    override suspend fun deleteCommentByPostId(postId: Long): Boolean = dbQuery {
        Comments.deleteWhere {
            Comments.postId eq postId
        } > 0
    }

    /**
     * 根据父评论 ID 数组删除评论
     * @param ids 父评论 ID
     */
    override suspend fun deleteCommentByParentIds(ids: List<Long>): Boolean = dbQuery {
        Comments.deleteWhere {
            parentCommentId inList ids
        } > 0
    }

    /**
     * 修改评论
     * 仅可修改以下字段：content、site、displayName、email、isPass
     * @param comment 评论数据类
     */
    override suspend fun updateComment(comment: Comment): Boolean = dbQuery {
        Comments.update({
            Comments.commentId eq comment.commentId
        }) {
            it[content] = comment.content
            it[site] = comment.site
            it[displayName] = comment.displayName
            it[email] = comment.email
            it[isPass] = comment.isPass
        } > 0
    }

    /**
     * 批量设置评论是否通过审核
     * @param ids 评论 ID 数组
     * @param isPass 是否通过审核
     */
    override suspend fun setCommentPass(ids: List<Long>, isPass: Boolean): Boolean = dbQuery {
        Comments.update({
            Comments.commentId inList ids
        }) {
            it[Comments.isPass] = isPass
        } > 0
    }

    /**
     * 根据评论 ID 获取所有评论
     * @param ids 评论 ID 数组
     */
    override suspend fun comments(ids: List<Long>): List<Comment> = dbQuery {
        Comments
            .selectAll()
            .where { Comments.commentId inList ids }
            .map(::resultRowToComment)
    }

    /**
     * 分页获取所有评论
     * @param page 当前页数
     * @param size 每页条数
     * @param postId 文章 ID
     * @param commentId 评论 ID
     * @param parentId 父评论 ID
     * @param isPass 是否通过审核
     * @param key 关键字
     * @param sort 排序方式（默认时间降序）
     */
    override suspend fun comments(
        page: Int,
        size: Int,
        postId: Long?,
        commentId: Long?,
        parentId: Long?,
        isPass: Boolean?,
        key: String?,
        sort: CommentSort?
    ): Pager<Comment> {
        val query = Comments
            .leftJoin(Posts, additionalConstraint = {
                Posts.postId eq Comments.postId
            })
            .selectAll()

        postId?.let { query.andWhere { Comments.postId eq it } }
        commentId?.let { query.andWhere { Comments.commentId eq it } }
        parentId?.let { query.andWhere { Comments.parentCommentId eq it } }
        isPass?.let { query.andWhere { Comments.isPass eq it } }
        key?.let { query.andWhere { Comments.content like "%$it%" or (Comments.email like "%$it%") or (Comments.displayName like "%$it%") } }
        when (sort) {
            CommentSort.CREATE_TIME_DESC -> query.orderBy(Comments.createTime, SortOrder.DESC)
            CommentSort.CREATE_TIME_ASC -> query.orderBy(Comments.createTime, SortOrder.ASC)
            else -> query.orderBy(Comments.createTime, SortOrder.DESC)
        }

        return if (page == 0) {
            dbQuery {
                val comments = query.map(::resultRowToComment)
                // 不分页
                Pager(0, 0, comments, comments.size.toLong(), 1)
            }
        } else {
            Comments.startPage(page, size, ::resultRowToComment) { query }
        }
    }

    /**
     * 根据评论 ID 获取评论
     * @param id 评论 ID
     */
    override suspend fun commentById(id: Long): Comment? = dbQuery {
        Comments
            .selectAll()
            .where { Comments.commentId eq id }
            .map(::resultRowToComment)
            .singleOrNull()
    }

    /**
     * 获取评论数量
     */
    override suspend fun commentCount(): Long = dbQuery {
        Comments.selectAll().count()
    }
}