package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 日记表
 */
object Diaries : Table("diary") {
    /** 日记 ID **/
    val diaryId = integer("diaryId").autoIncrement()
    /** 日记内容 **/
    val content = text("content")
    /** HTML（由 content 解析得来） **/
    val html = text("html")
    /** 创建时间 **/
    val createTime = long("createTime")
    /** 最后修改时间 **/
    val lastModifyTime = long("lastModifyTime").nullable()
}