package cc.loac.data.models


/**
 * 文章标签数据类
 * @param tagId 标签 ID
 * @param displayName 标签名
 * @param slug 标签别名
 * @param color 标签颜色
 * @param postCount 文章数量
 */
data class Tag(
    val tagId: Long,
    val displayName: String,
    val slug: String,
    val color: String?,
    val postCount: Long? = null
)
