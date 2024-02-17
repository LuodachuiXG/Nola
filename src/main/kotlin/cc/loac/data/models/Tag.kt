package cc.loac.data.models


/**
 * 文章标签数据类
 */
data class Tag(
    /** 标签 ID **/
    val tagId: Int,
    /** 标签名 **/
    val displayName: String,
    /** 标签别名 **/
    val slug: String
)
