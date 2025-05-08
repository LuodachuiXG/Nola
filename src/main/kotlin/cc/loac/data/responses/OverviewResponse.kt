package cc.loac.data.responses

import cc.loac.data.models.Post

/**
 * 概述响应数据类
 * @param count 项目数量
 * @param mostViewedPost 浏览量最多的文章
 * @param createDate 博客创建时间
 */
data class OverviewResponse(
    val count: OverviewCount,
    val mostViewedPost: Post,
    val createDate: Long
)


/**
 * 概述项目数量
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
    val post: Int,
    val tag: Int,
    val category: Int,
    val comment: Int,
    val diary: Int,
    val file: Int,
    val link: Int,
    val menu: Int
)