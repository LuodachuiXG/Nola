package cc.loac.data.requests

import cc.loac.data.models.enums.MenuItemTarget

/**
 * 菜单项请求数据类
 */
data class MenuItemRequest(
    /** 菜单项 ID */
    val menuItemId: Long?,
    /** 菜单名称 */
    val displayName: String,
    /** 菜单地址 */
    val href: String,
    /** 打开方式 */
    val target: MenuItemTarget? = MenuItemTarget.BLANK,
    /** 父菜单 ID */
    val parentMenuId: Long,
    /** 父菜单项 ID */
    val parentMenuItemId: Long?,
    /** 菜单项排序索引 **/
    var index: Int
)
