package cc.loac.data.models

import cc.loac.data.models.enums.PostContentStatus

/**
 * 文章内容数据类
 * @param postContentId 文章内容 ID
 * @param postId 文章 ID
 * @param content 内容
 * @param html HTML（由 content 解析得来）
 * @param status 状态
 * @param draftName 草稿名
 * @param lastModifyTime 最后修改时间
 */
data class PostContent(
    val postContentId: Long,
    val postId: Long,
    val content: String,
    val html: String,
    val status: PostContentStatus,
    val draftName: String?,
    val lastModifyTime: Long?
)
