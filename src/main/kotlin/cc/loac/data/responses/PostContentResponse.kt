package cc.loac.data.responses

import cc.loac.data.models.enums.PostContentStatus

/**
 * 文章内容响应数据类
 * 用于返回文章正文和所有草稿
 */
data class PostContentResponse(
    val postId: Int,
    val status: PostContentStatus,
    val draftName: String?,
    val lastModifyTime: Long?
)
