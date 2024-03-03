package cc.loac.data.sql.tables

import cc.loac.data.models.enums.MenuItemTarget
import org.jetbrains.exposed.sql.Table

/**
 * 菜单项表
 */
object MenuItems : Table("menu_item") {
    /** 菜单项 ID **/
    val menuItemId = integer("menuItemId").autoIncrement()

    /** 菜单项名称 **/
    val displayName = varchar("displayName", 256)

    /** 菜单项地址 **/
    val href = varchar("href", 512).nullable()

    /** 菜单项打开方式 **/
    val target = enumerationByName<MenuItemTarget>("target", 12)

    /** 父菜单 ID **/
    val parentMenuId = integer("parentMenuId").references(Menus.menuId)

    /** 父菜单项 ID **/
    val parentMenuItemId = integer("parentMenuItemId").references(menuItemId).nullable()

    /** 创建时间 **/
    val createTime = long("createTime")

    override val primaryKey = PrimaryKey(menuItemId)
}