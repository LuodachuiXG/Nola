package cc.loac.data.responses

import cc.loac.data.models.Category
import cc.loac.data.models.Tag

/**
 * 博客 API 文章响应类
 */
data class ApiPostResponse(
    val postId: Int,
    val title: String,
    val excerpt: String?,
    val slug: String,
    val cover: String?,
    val allowComment: Boolean,
    val pinned: Boolean?,
    val encrypted: Boolean,
    val visit: Int,
    var category: Category?,
    var tags: List<Tag>,
    val createTime: Long,
    val lastModifyTime: Long?
)