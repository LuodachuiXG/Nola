package cc.loac.data.sql.tables

import cc.loac.data.models.enums.PostContentStatus
import org.jetbrains.exposed.sql.Table

/**
 * 文章内容表
 */
object PostContents : Table("post_content") {
    /** 文章内容 ID **/
    val postContentId = integer("postContentId").autoIncrement()

    /** 文章 ID **/
    val postId = integer("postId").references(Posts.postId)

    /** 内容 **/
    val content = text("content", "utf8mb4_general_ci")

    /** HTML（由 content 解析得来） **/
    val html = text("html", "utf8mb4_general_ci")

    /** 状态 **/
    val status = enumerationByName("status", 24, PostContentStatus::class)

    /** 草稿名 **/
    val draftName = varchar("draftName", 256).nullable()

    /** 最后修改时间 **/
    val lastModifyTime = long("lastModifyTime").nullable()

    override val primaryKey = PrimaryKey(postContentId)
}