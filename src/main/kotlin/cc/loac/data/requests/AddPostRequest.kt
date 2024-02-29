package cc.loac.data.requests

import cc.loac.data.models.enums.PostVisible

/**
 * 添加文章请求数据类
 */
data class AddPostRequest(
    /** 标题 **/
    val title: String,
    /** 摘要（为 null 自动根据内容生成） **/
    var excerpt: String?,
    /** 别名 **/
    val slug: String,
    /** 是否允许评论 **/
    val allowComment: Boolean,
    /** 可见性 **/
    val visible: PostVisible,
    /** 文章内容（Markdown 或普通文本） **/
    val content: String,
    /** 分类 ID **/
    val categoryId: Int?,
    /** 标签 ID 集合 **/
    val tagIds: List<Int>?,
    /** 封面（优先级高于分类封面） **/
    val cover: String?,
    /** 是否置顶 **/
    val pinned: Boolean?,
    /** 密码 **/
    val password: String?
)
