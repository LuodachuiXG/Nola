package cc.loac.data.models


/**
 * 博客信息数据类
 * @param title 标题
 * @param subtitle 副标题
 * @param blogger 博主（User 的显示名称 displayName）
 * @param logo LOGO
 * @param favicon Favicon
 * @param createDate 创建时间
 */
data class BlogInfo (
    val title: String?,
    val subtitle: String?,
    val blogger: String? = null,
    val logo: String? = null,
    val favicon: String? = null,
    val createDate: Long? = null
)
