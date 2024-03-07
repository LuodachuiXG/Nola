package cc.loac.data.responses

import cc.loac.data.models.enums.PostContentStatus

/**
 * 文章内容响应数据类
 * 用于返回文章正文和所有草稿
 */
data class PostContentResponse(
    /** 文章内容 ID */
    val postContentId: Int,
    /** 文章 ID */
    val postId: Int,
    /** 文章内容状态 */
    val status: PostContentStatus,
    /** 草稿名称 */
    val draftName: String?,
    /** 最后修改时间 */
    val lastModifyTime: Long?
)
