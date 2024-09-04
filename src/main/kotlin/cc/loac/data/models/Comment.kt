package cc.loac.data.models

/**
 * 评论数据类
 * @param commentId 评论 ID
 * @param postId 文章 ID
 * @param parentCommentId 父评论 ID
 * @param replayCommentId 回复评论 ID
 * @param replayDisplayName 回复评论用户名
 * @param content 评论内容
 * @param site 评论人站点
 * @param displayName 评论人名称
 * @param email 评论人邮箱
 * @param createTime 评论时间
 * @param isPass 是否通过审核
 */
data class Comment(
    val commentId: Long = -1,
    val postId: Long,
    val parentCommentId: Long? = null,
    val replayCommentId: Long? = null,
    val replayDisplayName: String? = null,
    val content: String,
    val site: String? = null,
    val displayName: String,
    val email: String? = null,
    val createTime: Long,
    val isPass: Boolean = false
)