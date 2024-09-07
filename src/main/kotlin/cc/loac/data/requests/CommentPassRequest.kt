package cc.loac.data.requests

/**
 * 评论通过审核请求类
 * @param ids 评论 ID 集合
 * @param isPass 是否通过审核
 */
data class CommentPassRequest(
    val ids: List<Long>,
    val isPass: Boolean
)