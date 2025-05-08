package cc.loac.data.responses

import cc.loac.data.models.Post

/**
 * 概述响应数据类
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
    val tags: List<OverviewTag>,
    val categories: List<OverviewCategory>,
    val mostViewedPost: Post,
    val lastOperation: String,
    val lastLoginDate: Long,
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

/**
 * 概述标签数据
 * @param tagId 标签 ID
 * @param displayName 标签名
 * @param postCount 文章数量
 */
data class OverviewTag(
    val tagId: Long,
    val displayName: String,
    val postCount: Int
)

/**
 * 概述分类数据
 */
data class OverviewCategory(
    val categoryId: Long,
    val displayName: String,
    val postCount: Int
)