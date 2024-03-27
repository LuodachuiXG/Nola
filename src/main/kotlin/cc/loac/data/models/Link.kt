package cc.loac.data.models

/**
 * 友情链接数据类
 * @param linkId 友情链接 ID
 * @param displayName 链接名称
 * @param url 链接地址
 * @param logo logo 地址
 * @param description 描述
 * @param priority 优先级（0默认，1 - 10）
 * @param remark 备注
 * @param createTime 创建时间戳
 * @param lastModifyTime 最后修改时间戳
 */
data class Link(
    val linkId: Int,
    val displayName: String,
    val url: String,
    val logo: String?,
    val description: String?,
    val priority: Int = 0,
    val remark: String?,
    val createTime: Long,
    val lastModifyTime: Long?
)