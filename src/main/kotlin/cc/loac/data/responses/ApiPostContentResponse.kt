package cc.loac.data.responses

/**
 * 博客 API 文章内容响应类
 * @param post 文章信息
 * @param content 文章正文
 */
data class ApiPostContentResponse(
    val post: ApiPostResponse,
    val content: String
)
