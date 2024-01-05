package cc.loac.sql.models

import org.jetbrains.exposed.sql.Table


data class Article(
    val id: Int,
    val title: String,
    val body: String
)

/**
 * 文章表
 */
object Articles: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}