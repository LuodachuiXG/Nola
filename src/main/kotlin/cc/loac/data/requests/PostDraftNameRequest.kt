package cc.loac.data.requests

/**
 * 文章草稿名请求数据类
 */
data class PostDraftNameRequest(
    /** 文章 ID **/
    val postId: Int,
    /** 旧草稿名 */
    val oldName: String,
    /** 新草稿名 */
    val newName: String
)
