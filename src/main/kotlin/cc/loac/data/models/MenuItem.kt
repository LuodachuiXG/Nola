package cc.loac.data.models

import cc.loac.data.models.enums.MenuItemTarget

/**
 * 菜单项数据类
 * @param menuItemId 菜单项 ID
 * @param displayName 菜单名
 * @param href 菜单地址
 * @param target 打开方式
 * @param parentMenuId 父菜单 ID
 * @param parentMenuItemId 父菜单项 ID
 * @param index 菜单项排序索引
 * @param createTime 创建时间
 * @param lastModifyTime 最后修改时间
 */
data class MenuItem(
    val menuItemId: Int,
    val displayName: String,
    val href: String,
    val target: MenuItemTarget,
    val parentMenuId: Int,
    val parentMenuItemId: Int?,
    val index: Int,
    val createTime: Long,
    val lastModifyTime: Long?
)
