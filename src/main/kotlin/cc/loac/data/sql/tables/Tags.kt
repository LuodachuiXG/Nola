package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章标签表
 */
object Tags : Table("tag") {
    /** 标签 ID **/
    val tagId = integer("tagId").autoIncrement()
    /** 标签名 **/
    val displayName = varchar("displayName", 256)
    /** 标签别名 **/
    val slug = varchar("slug", 256)

    override val primaryKey = PrimaryKey(tagId)
}