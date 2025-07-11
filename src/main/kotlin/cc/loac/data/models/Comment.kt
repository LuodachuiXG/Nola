package cc.loac.data.models

import java.util.*

/**
 * 评论数据类
 * @param commentId 评论 ID
 * @param postId 文章 ID
 * @param postTitle 文章标题
 * @param parentCommentId 父评论 ID
 * @param replyCommentId 回复评论 ID
 * @param replyDisplayName 回复评论用户名
 * @param content 评论内容
 * @param site 评论人站点
 * @param displayName 评论人名称
 * @param email 评论人邮箱
 * @param createTime 评论时间
 * @param isPass 是否通过审核
 * @param children 子评论集合
 */
data class Comment(
    val commentId: Long = -1,
    val postId: Long,
    val postTitle: String? = null,
    val parentCommentId: Long? = null,
    val replyCommentId: Long? = null,
    val replyDisplayName: String? = null,
    val content: String,
    val site: String? = null,
    val displayName: String,
    val email: String,
    val createTime: Long = Date().time,
    val isPass: Boolean = false,
    var children: LinkedList<Comment>? = null
)