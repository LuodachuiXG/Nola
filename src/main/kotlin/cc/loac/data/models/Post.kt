package cc.loac.data.models

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible

/**
 * 文章数据类
 */
data class Post(
    /** 文章 ID **/
    val postId: Int = -1,
    /** 标题 **/
    val title: String,
    /** 摘要 **/
    val excerpt: String,
    /** 别名 **/
    val slug: String,
    /** 封面 **/
    val cover: String?,
    /** 是否允许评论 **/
    val allowComment: Boolean,
    /** 是否置顶 **/
    val pinned: Boolean?,
    /** 状态 **/
    val status: PostStatus,
    /** 可见性 **/
    val visible: PostVisible,
    /** 是否加密 **/
    val encrypted: Boolean,
    /** 密码 **/
    val password: String?,
    /** 文章分类 **/
    var category: Category?,
    /** 文章标签 **/
    var tags: List<Tag>,
    /** 创建时间 **/
    val createTime: Long,
    /** 最后修改时间 **/
    val lastModifyTime: Long?
)
