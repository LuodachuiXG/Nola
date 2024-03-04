package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Menu
import cc.loac.data.models.MenuItem
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.data.responses.MenuItemResponse
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.MenuDao
import cc.loac.services.MenuService
import cc.loac.utils.error
import io.ktor.utils.io.*
import kotlinx.css.hr
import kotlinx.css.th
import kotlinx.css.tr
import org.koin.java.KoinJavaComponent.inject

private val menuDao: MenuDao by inject(MenuDao::class.java)

/**
 * 菜单服务接口实现类
 */
class MenuServiceImpl : MenuService {

    /**
     * 添加菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun addMenu(menuRequest: MenuRequest): Menu? {
        // 先检查菜单名是否已经存在
        if (menu(menuRequest.displayName) != null)
            throw MyException("菜单名 [${menuRequest.displayName}] 已存在")
        // 判断当前菜单是否是第一个菜单
        if (menuDao.menuCount() == 0L) {
            // 将第一个菜单默认设置成主菜单
            menuRequest.isMain = true
        }
        return menuDao.addMenu(menuRequest)
    }

    /**
     * 删除菜单
     * @param menuIds 菜单 ID 集合
     */
    override suspend fun deleteMenu(menuIds: List<Int>): Boolean {
        return menuDao.deleteMenu(menuIds)
    }

    /**
     * 修改菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun updateMenu(menuRequest: MenuRequest): Boolean {
        // 判断菜单名是否重复
        val menu = menu(menuRequest.displayName)
        if (menu != null && menu.menuId != menuRequest.menuId)
            throw MyException("菜单名 [${menuRequest.displayName}] 已存在")
        return menuDao.updateMenu(menuRequest)
    }

    /**
     * 获取菜单
     * @param menuId 菜单 ID
     */
    override suspend fun menu(menuId: Int): Menu? {
        return menuDao.menu(menuId)
    }

    /**
     * 获取菜单
     * @param displayName 菜单名
     */
    override suspend fun menu(displayName: String): Menu? {
        return menuDao.menu(displayName)
    }

    /**
     * 获取所有菜单
     */
    override suspend fun menus(): List<Menu> {
        return menuDao.menus()
    }

    /**
     * 分页获取所有菜单
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun menus(page: Int, size: Int): Pager<Menu> {
        if (page == 0) {
            // 获取所有菜单
            val menus = menus()
            return Pager(0, 0, menus, menus.size.toLong(), 1)
        }
        return menuDao.menus(page, size)
    }

    /**
     * 添加菜单项
     * @param menuItem 菜单项请求数据类
     */
    override suspend fun addMenuItem(menuItem: MenuItemRequest): MenuItem? {
        // 添加前先检查父菜单是否存在
        menu(menuItem.parentMenuId) ?: throw MyException("父菜单 [${menuItem.parentMenuId}] 不存在")
        // 如果父菜单项不为空，再检查一下父菜单项是否存在
        if (menuItem.parentMenuItemId != null) {
            menuItem(menuItem.parentMenuItemId) ?: throw MyException("父菜单项 [${menuItem.parentMenuItemId}] 不存在")
        }
        return menuDao.addMenuItem(menuItem)
    }

    /**
     * 删除菜单项
     * @param menuItemIds 菜单项 ID 集合
     */
    override suspend fun deleteMenus(menuItemIds: List<Int>): Boolean {
        // 先获取所有菜单项
        val menuItems = menuDao.menuItems()
        // 获取要删除的所有菜单项的所有子菜单项 ID
        val childrenIds = mutableListOf<Int>()
        menuItemIds.forEach { menuId ->
            childrenIds.addAll(findChildrenIds(menuId, menuItems))
        }
        // 将要删除的菜单项 ID 集合也加入子菜单项 ID 集合中，一起删除
        childrenIds.addAll(menuItemIds)
        // 删除菜单项
        childrenIds.toString().error()
        return menuDao.deleteMenuItems(childrenIds)
    }

    /**
     * 查找子菜单项 ID
     * @param parentMenuItemId 父菜单项 ID
     * @param menuItems 菜单项列表
     */
    private fun findChildrenIds(parentMenuItemId: Int, menuItems: List<MenuItem>): List<Int> {
        val childrenIds = mutableListOf<Int>()
        menuItems.forEach { menuItem ->
            if (menuItem.parentMenuItemId == parentMenuItemId) {
                childrenIds.add(menuItem.menuItemId)
                // 再递归查找子菜单的子菜单
                childrenIds.addAll(findChildrenIds(menuItem.menuItemId, menuItems))
            }
        }
        return childrenIds
    }

    /**
     * 修改菜单项
     * @param menuItem 菜单项请求数据类
     */
    override suspend fun updateMenuItem(menuItem: MenuItemRequest): Boolean {
        // 判断是否将自己设为父菜单项
        if (menuItem.parentMenuItemId == menuItem.menuItemId)
            throw MyException("不能将自己设为父菜单项")
        // 修改前先检查父菜单是否存在
        menu(menuItem.parentMenuId) ?: throw MyException("父菜单 [${menuItem.parentMenuId}] 不存在")
        // 如果父菜单项不为空
        if (menuItem.parentMenuItemId != null) {
            // 父菜单项是否存在
            val parentMenuItem = menuItem(menuItem.parentMenuItemId)
                ?: throw MyException("父菜单项 [${menuItem.parentMenuItemId}] 不存在")
            // 判断父菜单项和和当前修改的菜单项是否在同一个父菜单下
            if (parentMenuItem.parentMenuId != menuItem.parentMenuId) {
                throw MyException("父菜单项和和当前菜单项不在相同父菜单")
            }
            // 如果父菜单项为当前修改菜单的子菜单项（循环设置父菜单，即父菜单项的子菜单项又被设为为父菜单项的父菜单项）
            if (parentMenuItem.parentMenuItemId == menuItem.menuItemId) {
                // 删除原先的子菜单项的父菜单项
                updateMenuItem(
                    MenuItemRequest(
                        menuItemId = parentMenuItem.menuItemId,
                        displayName = parentMenuItem.displayName,
                        href = parentMenuItem.href,
                        target = parentMenuItem.target,
                        parentMenuId = parentMenuItem.parentMenuId,
                        // 将原先的子菜单的（当前要修改的菜单项的父菜单项）的父菜单项置为空
                        parentMenuItemId = null
                    )
                )
            }
        }
        return menuDao.updateMenuItem(menuItem)
    }

    /**
     * 获取菜单项
     * @param menuItemId 菜单项 ID
     */
    override suspend fun menuItem(menuItemId: Int): MenuItem? {
        return menuDao.menuItem(menuItemId)
    }

    /**
     * 获取菜单项（平铺，不构建菜单项树）
     * @param menuId 菜单 ID
     */
    override suspend fun menuItems(menuId: Int, buildTree: Boolean): List<MenuItemResponse> {
        // 先获取当前菜单所有菜单项
        val menuItems = menuDao.menuItems(menuId)
        // 是否构建菜单项树
        if (buildTree) {
            // 构建菜单项树
            return findMenuItemChildren(null, menuItems)
        }
        // 不构建菜单项树
        return menuItems.map {
            MenuItemResponse(
                menuId = menuId,
                menuItemId = it.menuItemId,
                displayName = it.displayName,
                href = it.href,
                target = it.target,
                parentMenuItemId = it.parentMenuItemId,
                lastModifyTime = it.lastModifyTime,
                createTime = it.createTime
            )
        }
    }

    /**
     * 获取主菜单的菜单项
     * @param buildTree 是否构建菜单项树
     */
    override suspend fun mainMenu(buildTree: Boolean): List<MenuItemResponse> {
        // 获取主菜单的菜单项
        val menuItems = menuDao.mainMenuItems()
        // 是否构建菜单项树
        if (buildTree) {
            // 构建菜单项树
            return findMenuItemChildren(null, menuItems)
        }
        // 不构建菜单项树
        return menuItems.map {
            MenuItemResponse(
                menuId = it.parentMenuId,
                menuItemId = it.menuItemId,
                displayName = it.displayName,
                href = it.href,
                target = it.target,
                parentMenuItemId = it.parentMenuItemId,
                lastModifyTime = it.lastModifyTime,
                createTime = it.createTime
            )
        }
    }

    /**
     * 递归函数，查找并构建子菜单项
     * @param parentMenuItemId 父菜单项 ID
     * @param items 菜单项列表
     */
    private fun findMenuItemChildren(
        parentMenuItemId: Int?,
        items: List<MenuItem>
    ): List<MenuItemResponse> {
        return items.filter { it.parentMenuItemId == parentMenuItemId }
            .map { menuItem ->
                MenuItemResponse(
                    menuId = menuItem.parentMenuId,
                    menuItemId = menuItem.menuItemId,
                    displayName = menuItem.displayName,
                    href = menuItem.href,
                    target = menuItem.target,
                    parentMenuItemId = menuItem.parentMenuItemId,
                    // 递归查找子菜单
                    children = findMenuItemChildren(menuItem.menuItemId, items),
                    lastModifyTime = menuItem.lastModifyTime,
                    createTime = menuItem.createTime
                )
            }
    }
}