package cc.loac.data.models

import cc.loac.data.models.enums.PostContentStatus

data class PostContent(
    /** 文章内容 ID **/
    val postContentId: Int,

    /** 文章 ID **/
    val postId: Int,

    /** 内容 **/
    val content: String,

    /** 状态 **/
    val status: PostContentStatus,

    /** 草稿名 **/
    val draftName: String?,

    /** 最后修改时间 **/
    val lastModifyTime: Long?
)
