package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 友情链接表
 */
object Links : Table("link") {
    /** 友情链接 ID **/
    val linkId = long("link_id").autoIncrement()

    /** 链接名称 **/
    val displayName = varchar("display_name", 128)

    /** 链接地址 **/
    val url = varchar("url", 512)

    /** logo 地址 **/
    val logo = varchar("logo", 512).nullable()

    /** 描述 **/
    val description = varchar("description", 512).nullable()

    /** 优先级（0默认，1 - 100） **/
    val priority = integer("priority").default(0)

    /** 备注 **/
    val remark = varchar("remark", 256).nullable()

    /** 是否已失联 **/
    val isLost = bool("is_lost").default(false)

    /** 创建时间戳 **/
    val createTime = long("create_time")

    /** 最后修改时间戳 **/
    val lastModifyTime = long("last_modify_time").nullable()

    override val primaryKey = PrimaryKey(linkId)
}