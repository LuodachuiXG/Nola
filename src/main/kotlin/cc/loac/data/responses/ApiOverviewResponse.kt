package cc.loac.data.responses

import cc.loac.data.models.Category
import cc.loac.data.models.Post
import cc.loac.data.models.Tag

/**
 * 博客 API 概览响应数据类
 * @param postVisitCount 文章总浏览量
 * @param tags 文章最多的 6 个标签
 * @param categories 文章最多的 6 个分类
 * @param mostViewedPost 浏览量最多的文章
 * @param createDate 博客创建时间
 */
data class ApiOverviewResponse(
    val postVisitCount: Long,
    val count: ApiOverviewCount,
    val tags: List<Tag>,
    val categories: List<Category>,
    val mostViewedPost: ApiPostResponse?,
    val createDate: Long?
)

/**
 * 博客 API 概览项目数量
 * @param post 文章数量
 * @param tag 标签数量
 * @param category 分类数量
 * @param comment 评论数量
 * @param diary 日记数量
 * @param link 链接数量
 */
data class ApiOverviewCount(
    val post: Long,
    val tag: Long,
    val category: Long,
    val comment: Long,
    val diary: Long,
    val link: Long
)