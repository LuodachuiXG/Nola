package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 日记表
 */
object Diaries : Table("diary") {
    /** 日记 ID **/
    val diaryId = long("diary_id").autoIncrement()
    /** 日记内容 **/
    val content = text("content", "utf8mb4_general_ci")
    /** HTML（由 content 解析得来） **/
    val html = text("html", "utf8mb4_general_ci")
    /** 创建时间 **/
    val createTime = long("create_time")
    /** 最后修改时间 **/
    val lastModifyTime = long("last_modify_time").nullable()

    override val primaryKey = PrimaryKey(diaryId)
}