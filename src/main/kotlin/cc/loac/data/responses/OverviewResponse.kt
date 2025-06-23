package cc.loac.data.responses

import cc.loac.data.models.Category
import cc.loac.data.models.Post
import cc.loac.data.models.Tag

/**
 * 概览响应数据类
 * @param count 项目数量
 * @param tags 标签列表
 * @param categories 分类列表
 * @param mostViewedPost 浏览量最多的文章
 * @param lastOperation 最近的一次操作
 * @param lastLoginDate 最后登录时间
 * @param createDate 博客创建时间
 */
data class OverviewResponse(
    val count: OverviewCount,
    val tags: List<Tag>,
    val categories: List<Category>,
    val mostViewedPost: Post?,
    val lastOperation: String?,
    val lastLoginDate: Long?,
    val createDate: Long?
)

/**
 * 概览项目数量
 * @param post 文章数量
 * @param tag 标签数量
 * @param category 分类数量
 * @param comment 评论数量
 * @param diary 日记数量
 * @param file 文件数量
 * @param link 链接数量
 * @param menu 菜单数量
 */
data class OverviewCount(
    val post: Long,
    val tag: Long,
    val category: Long,
    val comment: Long,
    val diary: Long,
    val file: Long,
    val link: Long,
    val menu: Long
)