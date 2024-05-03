package cc.loac.data.requests

/**
 * 修改博客信息请求数据类
 * @param title 标题
 * @param subtitle 副标题
 * @param logo Logo
 * @param favicon Favicon
 */
data class BlogInfoRequest(
    val title: String,
    val subtitle: String?,
    val logo: String?,
    val favicon: String?
)
