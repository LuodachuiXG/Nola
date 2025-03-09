package cc.loac.data.requests

/**
 * 评论请求类
 * @param commentId 评论 ID
 * @param postId 文章 ID
 * @param parentCommentId 父评论 ID
 * @param replyCommentId 回复评论 ID
 * @param content 评论内容
 * @param site 评论人站点
 * @param displayName 评论人名称
 * @param email 评论人邮箱
 * @param isPass 是否通过审核
 */
data class CommentRequest(
    val commentId: Long? = null,
    val postId: Long = -1,
    val parentCommentId: Long? = null,
    val replyCommentId: Long? = null,
    val content: String,
    val site: String? = null,
    val displayName: String,
    val email: String,
    val isPass: Boolean = false
)