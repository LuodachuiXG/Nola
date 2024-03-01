package cc.loac.data.sql.tables

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import org.jetbrains.exposed.sql.Table

/**
 * 文章表
 */
object Posts : Table("post") {
    /** 文章 ID **/
    val postId = integer("postId").autoIncrement()

    /** 标题 **/
    val title = varchar("title", 256)

    /** 摘要 **/
    val excerpt = varchar("excerpt", 512)

    /** 别名 **/
    val slug = varchar("slug", 256).uniqueIndex()

    /** 封面 **/
    val cover = varchar("cover", 256).nullable()

    /** 是否允许评论 **/
    val allowComment = bool("allowComment")

    /** 是否置顶 **/
    val pinned = bool("pinned")

    /** 状态 **/
    val status = enumerationByName("status", 50, PostStatus::class)

    /** 可见性 **/
    val visible = enumerationByName("visible", 50, PostVisible::class)

    /** 密码 **/
    val password = varchar("password", 100).nullable()

    /** 访问量 **/
    val visit = integer("visit").default(0)

    /** 创建时间 **/
    val createTime = long("createTime")

    /** 最后修改时间 **/
    val lastModifyTime = long("lastModifyTime").nullable()

    override val primaryKey = PrimaryKey(postId)
}