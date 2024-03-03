package cc.loac.services

import cc.loac.data.models.Menu
import cc.loac.data.requests.MenuRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.tables.Menus

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
}