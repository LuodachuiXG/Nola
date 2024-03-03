package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Menu
import cc.loac.data.models.MenuItem
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.MenuDao
import cc.loac.services.MenuService
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
            menuItem(menuItem.parentMenuItemId) ?:
            throw MyException("父菜单项 [${menuItem.parentMenuItemId}] 不存在")
        }
        return menuDao.addMenuItem(menuItem)
    }

    /**
     * 获取菜单项
     * @param menuItemId 菜单项 ID
     */
    override suspend fun menuItem(menuItemId: Int): MenuItem? {
        return menuDao.menuItem(menuItemId)
    }
}