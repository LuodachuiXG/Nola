package cc.loac.data.requests

/**
 * 文章草稿转正文请求数据类
 * 接收将草稿转换为正文的请求
 */
data class PostDraft2ContentRequest(
    /** 文章 ID */
    val postId: Long,
    /** 草稿名 */
    val draftName: String,
    /** 是否删除原来的正文 **/
    val deleteContent: Boolean = false,
    /** 正文草稿名 **/
    val contentName: String? = null
)
