package cc.loac.data.responses

import cc.loac.data.models.enums.PostContentStatus

/**
 * 文章内容响应数据类
 * 用于返回文章正文和所有草稿
 * @param postContentId 文章内容 ID
 * @param postId 文章 ID
 * @param status 文章内容状态
 * @param draftName 草稿名称
 * @param lastModifyTime 最后修改时间
 */
data class PostContentResponse(
    val postContentId: Int,
    val postId: Int,
    val status: PostContentStatus,
    val draftName: String?,
    val lastModifyTime: Long?
)
