package cc.loac.data.responses

import cc.loac.data.models.enums.MenuItemTarget

/**
 * 菜单项响应数据类
 */
data class MenuItemResponse(
    /** 菜单项 ID */
    val menuItemId: Int,
    /** 菜单 ID */
    val menuId: Int,
    /** 菜单项名称 */
    val displayName: String,
    /** 菜单地址 */
    val href: String?,
    /** 打开方式 */
    val target: MenuItemTarget,
    /** 父菜单项 ID */
    val parentMenuItemId: Int?,
    /** 子菜单 */
    val children: List<MenuItemResponse> = emptyList(),
    /** 菜单项排序索引 **/
    val index: Int,
    /** 创建时间 */
    val createTime: Long,
    /** 最后修改时间 **/
    val lastModifyTime: Long?
)