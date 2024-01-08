package cc.loac.data.models.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

/**
 * 文章表
 */
object Articles: Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val body = varchar("body", 1024)
    val createDate = datetime("createDate").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(id)
}