package cc.loac.data.sql.tables

import cc.loac.data.models.enums.MenuItemTarget
import cc.loac.data.sql.tables.Menus.nullable
import org.jetbrains.exposed.sql.Table

/**
 * 菜单项表
 */
object MenuItems : Table("menu_item") {
    /** 菜单项 ID **/
    val menuItemId = long("menu_item_id").autoIncrement()

    /** 菜单项名称 **/
    val displayName = varchar("display_name", 128)

    /** 菜单项地址 **/
    val href = varchar("href", 512)

    /** 菜单项打开方式 **/
    val target = enumerationByName<MenuItemTarget>("target", 12)

    /** 父菜单 ID **/
    val parentMenuId = long("parent_menuId")

    /** 父菜单项 ID **/
    val parentMenuItemId = long("parent_menu_item_id").nullable()

    /** 菜单项排序索引 **/
    val index = integer("index")

    /** 创建时间 **/
    val createTime = long("create_time")

    /** 最后修改时间 **/
    val lastModifyTime = long("last_modify_time").nullable()

    override val primaryKey = PrimaryKey(menuItemId)
}