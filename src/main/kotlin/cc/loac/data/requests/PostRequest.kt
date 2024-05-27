package cc.loac.data.requests

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.utils.postName2Slug

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
    var slug: String,
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

/**
 * 第一篇文章
 */
fun firstPost(): PostRequest {
    return PostRequest(
        title = "这是你的第一篇文章",
        autoGenerateExcerpt = true,
        slug = "first",
        allowComment = true,
        status = PostStatus.PUBLISHED,
        visible = PostVisible.VISIBLE,
        content = "这是第一篇文章，欢迎使用 Nola。",
        categoryId = null,
        tagIds = emptyList(),
        cover = null,
        encrypted = false,
        excerpt = null,
        postId = null,
        password = null
    )
}


/**
 * 根据名称和内容新建文章请求数据类
 * @param name 文章名称
 * @param content 文章内容
 */
fun newPostRequestByNameAndContent(name: String, content: String): PostRequest {
    return PostRequest(
        postId = null,
        title = if (name.contains(".")) {
            val index = name.lastIndexOf(".")
            name.substring(0, index)
        } else {
            name
        },
        autoGenerateExcerpt = true,
        slug = name.postName2Slug(),
        excerpt = null,
        allowComment = true,
        status = PostStatus.PUBLISHED,
        visible = PostVisible.VISIBLE,
        content = content,
        categoryId = null,
        tagIds = null,
        cover = null,
        encrypted = false,
        password = null
    )
}
