package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章分类表
 */
object PostCategories : Table("post_category") {
    /** 文章分类 ID **/
    val postCategoryId = long("post_category_id").autoIncrement()

    /** 文章 ID **/
    val postId = long("post_id").references(Posts.postId)

    /** 分类 ID **/
    val categoryId = long("category_id").references(Categories.categoryId)

    override val primaryKey = PrimaryKey(postCategoryId)
}