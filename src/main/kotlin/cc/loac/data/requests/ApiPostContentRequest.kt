package cc.loac.data.requests

/**
 * 博客 API 获取文章内容请求数据类
 * @param id 文章 ID
 * @param slug 文章别名
 * @param password 文章密码
 */
data class ApiPostContentRequest(
    val id: Long? = null,
    val slug: String? = null,
    val password: String? = null
)
