package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章标签表
 */
object Tags : Table("tag") {
    /** 标签 ID **/
    val tagId = long("tag_id").autoIncrement()

    /** 标签名 **/
    val displayName = varchar("display_name", 256)

    /** 标签别名 **/
    val slug = varchar("slug", 128, "utf8mb4_general_ci").uniqueIndex()

    /** 标签颜色 **/
    val color = varchar("color", 24).nullable()

    override val primaryKey = PrimaryKey(tagId)
}