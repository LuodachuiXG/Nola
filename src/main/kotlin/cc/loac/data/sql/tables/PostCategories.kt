package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章分类表
 */
object PostCategories : Table("post_category") {
    /** 文章分类 ID **/
    val postCategoryId = integer("postCategoryId").autoIncrement()

    /** 文章 ID **/
    val postId = integer("postId").references(Posts.postId)

    /** 分类 ID **/
    val categoryId = integer("categoryId").references(Categories.categoryId)

    override val primaryKey = PrimaryKey(postCategoryId)
}