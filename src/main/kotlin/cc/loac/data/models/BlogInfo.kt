package cc.loac.data.models


/**
 * 博客信息数据类
 */
data class BlogInfo (
    /** 标题 **/
    val title: String?,
    /** 副标题 **/
    val subtitle: String?,
    /** 博主（User 的显示名称 displayName） **/
    val blogger: String? = null,
    /** LOGO **/
    val logo: String? = null,
    /** Favicon **/
    val favicon: String? = null,
    /** 创建时间 **/
    val createDate: Long? = null
)
