package cc.loac.data.responses

/**
 * 博客 API 文章内容响应类
 */
data class ApiPostContentResponse(
    /** 文章信息 **/
    val post: ApiPostResponse,
    /** 文章正文 **/
    val content: String
)
