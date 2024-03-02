package cc.loac.data.models

/**
 * 友情链接数据类
 */

data class Link(
    /** 友情链接 ID **/
    val linkId: Int,
    /** 链接名称 **/
    val displayName: String,
    /** 链接地址 **/
    val url: String,
    /** logo 地址 **/
    val logo: String?,
    /** 描述 **/
    val description: String?,
    /** 优先级（0默认，1 - 10） **/
    val priority: Int = 0,
    /** 备注 **/
    val remark: String?,
    /** 创建时间戳 **/
    val createTime: Long,
    /** 最后修改时间戳 **/
    val lastModifyTime: Long?
)