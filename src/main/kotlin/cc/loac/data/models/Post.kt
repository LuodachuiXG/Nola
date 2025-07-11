package cc.loac.data.models

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import java.io.Serializable

/**
 * 文章数据类
 * @param postId 文章 ID
 * @param title 标题
 * @param autoGenerateExcerpt 是否自动生成摘要
 * @param excerpt 摘要
 * @param slug 别名
 * @param cover 封面
 * @param allowComment 是否允许评论
 * @param pinned 是否置顶
 * @param status 状态
 * @param visible 可见性
 * @param encrypted 是否加密
 * @param password 密码
 * @param visit 访问量
 * @param category 文章分类
 * @param tags 文章标签
 * @param createTime 创建时间
 * @param lastModifyTime 最后修改时间
 */
data class Post(
    val postId: Long = -1,
    val title: String,
    val autoGenerateExcerpt: Boolean,
    val excerpt: String,
    val slug: String,
    val cover: String?,
    val allowComment: Boolean,
    val pinned: Boolean?,
    val status: PostStatus,
    val visible: PostVisible,
    val encrypted: Boolean,
    val password: String?,
    val visit: Int,
    var category: Category?,
    var tags: List<Tag>,
    val createTime: Long,
    val lastModifyTime: Long?
): Serializable
