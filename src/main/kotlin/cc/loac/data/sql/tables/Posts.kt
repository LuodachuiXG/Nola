package cc.loac.data.sql.tables

import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import org.jetbrains.exposed.sql.Table

/**
 * 文章表
 */
object Posts : Table("post") {
    /** 文章 ID **/
    val postId = long("post_id").autoIncrement()

    /** 标题 **/
    val title = varchar("title", 256)

    /** 是否自动生成摘要 **/
    val autoGenerateExcerpt = bool("auto_generate_excerpt")

    /** 摘要 **/
    val excerpt = varchar("excerpt", 1024, "utf8mb4_general_ci")

    /** 别名 **/
    val slug = varchar("slug", 128, "utf8mb4_general_ci").uniqueIndex()

    /** 封面 **/
    val cover = varchar("cover", 512).nullable()

    /** 是否允许评论 **/
    val allowComment = bool("allow_comment")

    /** 是否置顶 **/
    val pinned = bool("pinned")

    /** 状态 **/
    val status = enumerationByName("status", 24, PostStatus::class)

    /** 可见性 **/
    val visible = enumerationByName("visible", 24, PostVisible::class)

    /** 密码 **/
    val password = varchar("password", 64).nullable()

    /** 访问量 **/
    val visit = integer("visit").default(0)

    /** 创建时间 **/
    val createTime = long("create_time")

    /** 最后修改时间 **/
    val lastModifyTime = long("last_modify_time").nullable()

    override val primaryKey = PrimaryKey(postId)
}