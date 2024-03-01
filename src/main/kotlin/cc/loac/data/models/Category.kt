package cc.loac.data.models

/**
 * 文章分类数据类
 */
data class Category(
    /** 分类 ID **/
    val categoryId: Int,
    /** 分类名 **/
    val displayName: String,
    /** 分类别名 **/
    val slug: String,
    /** 封面 **/
    val cover: String?,
    /** 是否统一封面（未单独设置封面的文章，使用分类的封面）**/
    val unifiedCover: Boolean = false
)