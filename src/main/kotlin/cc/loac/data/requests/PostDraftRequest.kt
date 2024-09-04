package cc.loac.data.requests

/**
 * 文章草稿请求数据类
 */
data class PostDraftRequest(
    /** 文章 ID **/
    val postId: Long,
    /** 草稿名 **/
    val draftName: String,
    /** 草稿内容 **/
    val content: String
)
