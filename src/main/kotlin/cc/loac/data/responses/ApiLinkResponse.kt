package cc.loac.data.responses

/**
 * 博客 API 友情链接响应类
 * @param displayName 链接名称
 * @param url 链接地址
 * @param logo logo 地址
 * @param description 描述
 */
data class ApiLinkResponse(
    val displayName: String,
    val url: String,
    val logo: String?,
    val description: String?
)