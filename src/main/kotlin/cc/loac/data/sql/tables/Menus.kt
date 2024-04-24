package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 菜单表
 */
object Menus : Table("menu") {
    /** 菜单 ID **/
    val menuId = integer("menuId").autoIncrement()

    /** 是否是主菜单 **/
    val isMain = bool("isMain")

    /** 菜单名 **/
    val displayName = varchar("displayName", 128, "utf8mb4_general_ci").uniqueIndex()

    /** 创建时间 **/
    val createTime = long("createTime")

    /** 最后修改时间 **/
    val lastModifyTime = long("lastModifyTime").nullable()

    override val primaryKey = PrimaryKey(menuId)
}