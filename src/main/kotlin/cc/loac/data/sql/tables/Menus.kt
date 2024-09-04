package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table

/**
 * 菜单表
 */
object Menus : Table("menu") {
    /** 菜单 ID **/
    val menuId = long("menu_id").autoIncrement()

    /** 是否是主菜单 **/
    val isMain = bool("is_main")

    /** 菜单名 **/
    val displayName = varchar("display_name", 128, "utf8mb4_general_ci").uniqueIndex()

    /** 创建时间 **/
    val createTime = long("create_time")

    /** 最后修改时间 **/
    val lastModifyTime = long("last_modify_time").nullable()

    override val primaryKey = PrimaryKey(menuId)
}