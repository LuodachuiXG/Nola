package cc.loac.data.requests

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible

/**
 * 修改文章状态请求数据类
 */
data class PostStatusRequest(
    /** 文章 ID **/
    val postId: Int,
    /** 文章状态 **/
    val status: PostStatus?,
    /** 文章可见性 **/
    val visible: PostVisible?,
    /** 置顶 **/
    val pinned: Boolean?
)
