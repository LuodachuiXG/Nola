package cc.loac.data.models

/**
 * 文章分类数据类
 * @param categoryId 分类 ID
 * @param displayName 分类名
 * @param slug 分类别名
 * @param cover 封面
 * @param unifiedCover 是否统一封面（未单独设置封面的文章，使用分类的封面）
 * @param postCount 文章数量
 */
data class Category(
    val categoryId: Long,
    val displayName: String,
    val slug: String,
    val cover: String?,
    val unifiedCover: Boolean = false,
    val postCount: Long? = null
)