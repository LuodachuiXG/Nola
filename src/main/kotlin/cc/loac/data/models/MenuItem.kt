package cc.loac.data.models

import cc.loac.data.models.enums.MenuItemTarget

/**
 * 菜单项数据类
 */
data class MenuItem(
    /** 菜单项 ID */
    val menuItemId: Int,
    /** 菜单名称 */
    val displayName: String,
    /** 菜单地址 */
    val href: String?,
    /** 打开方式 */
    val target: MenuItemTarget,
    /** 父菜单 ID */
    val parentMenuId: Int,
    /** 父菜单项 ID */
    val parentMenuItemId: Int?,
    /** 创建时间 */
    val createTime: Long
)
