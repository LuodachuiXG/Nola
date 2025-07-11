package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章标签表
 */
object PostTags : Table("post_tag") {
    /** 文章标签 ID **/
    val postTagId = long("post_tag_id").autoIncrement()

    /** 文章 ID **/
    val postId = long("post_id").references(Posts.postId)

    /** 标签 ID **/
    val tagId = long("tag_id").references(Tags.tagId)

    override val primaryKey = PrimaryKey(postTagId)
}