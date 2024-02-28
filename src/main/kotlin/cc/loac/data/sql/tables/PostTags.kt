package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章标签表
 */
object PostTags : Table("post_tag") {
    /** 文章标签 ID **/
    val postTagId = integer("postTagId").autoIncrement()

    /** 文章 ID **/
    val postId = integer("postId").references(Posts.postId)

    /** 标签 ID **/
    val tagId = integer("tagId").references(Tags.tagId)

    override val primaryKey = PrimaryKey(postTagId)
}