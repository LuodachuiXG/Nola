package cc.loac.data.responses

import cc.loac.data.models.Category
import cc.loac.data.models.Tag

/**
 * 博客 API 文章响应类
 * @param postId 文章 ID
 * @param title 文章标题
 * @param excerpt 文章摘要
 * @param slug 文章别名
 * @param cover 文章封面
 * @param allowComment 是否允许评论
 * @param pinned 是否置顶
 * @param encrypted 文章是否加密
 * @param visit 文章访问量
 * @param category 文章分类
 * @param tags 文章标签
 * @param createTime 文章创建时间
 * @param lastModifyTime 文章最后修改时间
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