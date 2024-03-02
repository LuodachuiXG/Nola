package cc.loac.data.responses

/**
 * 博客 API 友情链接响应类
 */
data class ApiLinkResponse(
    /** 链接名称 **/
    val displayName: String,
    /** 链接地址 **/
    val url: String,
    /** logo 地址 **/
    val logo: String?,
    /** 描述 **/
    val description: String?
)