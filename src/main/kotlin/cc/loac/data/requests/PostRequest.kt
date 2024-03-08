package cc.loac.data.requests

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible

/**
 * 文章请求数据类
 */
data class PostRequest(
    /** 文章 ID **/
    val postId: Int?,
    /** 标题 **/
    val title: String,
    /** 是否自动生成摘要 **/
    val autoGenerateExcerpt: Boolean,
    /** 摘要 **/
    var excerpt: String?,
    /** 别名 **/
    val slug: String,
    /** 是否允许评论 **/
    val allowComment: Boolean,
    /** 状态 **/
    val status: PostStatus,
    /** 可见性 **/
    val visible: PostVisible,
    /** 文章内容（Markdown 或普通文本） **/
    val content: String?,
    /** 分类 ID **/
    val categoryId: Int?,
    /** 标签 ID 集合 **/
    val tagIds: List<Int>?,
    /** 封面（优先级高于分类封面） **/
    val cover: String?,
    /** 是否置顶 **/
    val pinned: Boolean = false,
    /** 文章是否加密 （为 true 时需提供 password，为 null 保持不变，为 false 删除密码）**/
    val encrypted: Boolean?,
    /** 密码 **/
    val password: String?
)
