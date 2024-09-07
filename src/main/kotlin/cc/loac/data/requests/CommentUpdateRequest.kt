package cc.loac.data.requests

/**
 * 修改评论请求类
 * @param commentId 评论 ID
 * @param content 评论内容
 * @param site 网站地址
 * @param displayName 评论者昵称
 * @param email 评论者邮箱
 * @param isPass 是否通过审核
 */
data class CommentUpdateRequest(
    val commentId: Long = -1,
    val content: String,
    val site: String,
    val displayName: String,
    val email: String,
    val isPass: Boolean
)