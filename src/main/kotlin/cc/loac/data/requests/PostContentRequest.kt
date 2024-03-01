package cc.loac.data.requests

/**
 * 文章内容请求数据类
 */
data class PostContentRequest(
    /** 文章 ID **/
    val postId: Int,
    /** 文章内容 **/
    val content: String
)
