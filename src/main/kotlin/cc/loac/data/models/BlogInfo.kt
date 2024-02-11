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
    val blogger: String?,
    /** LOGO **/
    val logo: String?,
    /** Favicon **/
    val favicon: String?,
    /** 创建时间 **/
    val createDate: Long?
)
