package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 文章分类表
 */
object Categories : Table("category") {
    /** 分类 ID **/
    val categoryId = long("categoryId").autoIncrement()

    /** 分类名 **/
    val displayName = varchar("displayName", 256)

    /** 分类别名 **/
    val slug = varchar("slug", 64, "utf8mb4_general_ci").uniqueIndex()

    /** 封面 **/
    val cover = varchar("cover", 256).nullable()

    /** 是否统一封面（未单独设置封面的文章，使用分类的封面）**/
    val unifiedCover = bool("unifiedCover")

    override val primaryKey = PrimaryKey(categoryId)
}