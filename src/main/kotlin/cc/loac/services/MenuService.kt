package cc.loac.services

import cc.loac.data.models.Menu
import cc.loac.data.models.MenuItem
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.data.responses.MenuItemResponse
import cc.loac.data.responses.Pager

/**
 * 菜单服务接口
 */
interface MenuService {

    /**
     * 添加菜单
     * @param menuRequest 菜单请求数据类
     */
    suspend fun addMenu(menuRequest: MenuRequest): Menu?

    /**
     * 删除菜单
     * @param menuIds 菜单 ID 集合
     */
    suspend fun deleteMenu(menuIds: List<Int>): Boolean

    /**
     * 修改菜单
     * @param menuRequest 菜单请求数据类
     */
    suspend fun updateMenu(menuRequest: MenuRequest): Boolean

    /**
     * 获取菜单
     * @param menuId 菜单 ID
     */
    suspend fun menu(menuId: Int): Menu?

    /**
     * 获取菜单
     * @param displayName 菜单名
     */
    suspend fun menu(displayName: String): Menu?

    /**
     * 获取所有菜单
     */
    suspend fun menus(): List<Menu>

    /**
     * 分页获取所有菜单
     * @param page 当前页数
     * @param size 每页条数
     */
    suspend fun menus(page: Int, size: Int): Pager<Menu>

    /**
     * 添加菜单项
     * @param menuItem 菜单项请求数据类
     */
    suspend fun addMenuItem(menuItem: MenuItemRequest): MenuItem?

    /**
     * 删除菜单项
     * @param menuItemIds 菜单项 ID 集合
     */
    suspend fun deleteMenus(menuItemIds: List<Int>): Boolean

    /**
     * 修改菜单项
     * @param menuItem 菜单项请求数据类
     */
    suspend fun updateMenuItem(menuItem: MenuItemRequest): Boolean

    /**
     * 获取菜单项
     * @param menuItemId 菜单项 ID
     */
    suspend fun menuItem(menuItemId: Int): MenuItem?

    /**
     * 获取菜单项（平铺，不构建菜单项树）
     * @param menuId 菜单 ID
     * @param buildTree 是否构建菜单项树
     */
    suspend fun menuItems(menuId: Int, buildTree: Boolean = true): List<MenuItemResponse>

    /**
     * 获取主菜单的菜单项
     * @param buildTree 是否构建菜单项树
     */
    suspend fun mainMenu(buildTree: Boolean): List<MenuItemResponse>
}