package cc.loac.data.responses

import cc.loac.data.models.Comment

/**
 * 博客 API 评论响应数据类
 * @param commentId 评论 ID
 * @param postId 文章 ID
 * @param parentCommentId 父评论 ID
 * @param replyCommentId 回复评论 ID
 * @param replyDisplayName 回复评论用户名
 * @param content 评论内容
 * @param site 评论人站点
 * @param displayName 评论人名称
 * @param createTime 评论时间
 * @param children 子评论集合
 */
data class ApiCommentResponse(
    val commentId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val replyCommentId: Long?,
    val replyDisplayName: String?,
    val content: String,
    val site: String?,
    val displayName: String,
    val createTime: Long,
    val children: List<ApiCommentResponse>?
)

/**
 * 将评论数据类转换为博客 API 评论响应数据类，抹去邮箱和审核状态等内部字段
 */
fun Comment.toApiCommentResponse(): ApiCommentResponse {
    return ApiCommentResponse(
        commentId = commentId,
        postId = postId,
        parentCommentId = parentCommentId,
        replyCommentId = replyCommentId,
        replyDisplayName = replyDisplayName,
        content = content,
        site = site,
        displayName = displayName,
        createTime = createTime,
        children = children?.map { it.toApiCommentResponse() }
    )
}

/**
 * 将评论分页数据转换为博客 API 评论分页数据
 */
fun Pager<Comment>.toApiCommentPager(): Pager<ApiCommentResponse> {
    return Pager(
        page = page,
        size = size,
        data = data.map { it.toApiCommentResponse() },
        totalData = totalData,
        totalPages = totalPages
    )
}
