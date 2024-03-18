package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Menu
import cc.loac.data.models.MenuItem
import cc.loac.data.models.enums.MenuItemTarget
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.MenuDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.MenuItems
import cc.loac.data.sql.tables.Menus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.*

/**
 * 菜单操作接口实现类
 */
class MenuDaoImpl : MenuDao {

    /**
     * 将数据库检索结果转为 [Menu] 菜单数据类
     */
    private fun resultToMenu(row: ResultRow) = Menu(
        menuId = row[Menus.menuId],
        isMain = row[Menus.isMain],
        displayName = row[Menus.displayName],
        createTime = row[Menus.createTime],
        lastModifyTime = row[Menus.lastModifyTime],
    )

    /**
     * 将数据库检索结果转为 [MenuItem] 菜单项数据类
     */
    private fun resultToMenuItem(row: ResultRow) = MenuItem(
        menuItemId = row[MenuItems.menuItemId],
        displayName = row[MenuItems.displayName],
        href = row[MenuItems.href],
        target = row[MenuItems.target],
        parentMenuId = row[MenuItems.parentMenuId],
        parentMenuItemId = row[MenuItems.parentMenuItemId],
        createTime = row[MenuItems.createTime],
        lastModifyTime = row[MenuItems.lastModifyTime],
        index = row[MenuItems.index]
    )


    /**
     * 添加菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun addMenu(menuRequest: MenuRequest): Menu? = dbQuery {
        val result = Menus.insert {
            it[displayName] = menuRequest.displayName
            it[isMain] = menuRequest.isMain
            it[createTime] = Date().time
        }.resultedValues?.singleOrNull()?.let(::resultToMenu)
        if (result != null && menuRequest.isMain) {
            // 如果当前菜单被设为了主菜单，就将其他所有菜单设为非主菜单
            setMainMenu(result.menuId)
        }
        result
    }

    /**
     * 删除菜单
     * @param menuIds 菜单 ID 集合
     */
    override suspend fun deleteMenu(menuIds: List<Int>): Boolean = dbQuery {
        val result = Menus.deleteWhere { menuId inList menuIds } > 0
        // 同时删除被删除的菜单下的菜单项
        MenuItems.deleteWhere { parentMenuId inList menuIds }
        result
    }

    /**
     * 修改菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun updateMenu(menuRequest: MenuRequest): Boolean = dbQuery {
        val result = Menus.update({
            Menus.menuId eq menuRequest.menuId!!
        }) {
            it[displayName] = menuRequest.displayName
            it[isMain] = menuRequest.isMain
            it[lastModifyTime] = Date().time
        } > 0
        if (result && menuRequest.isMain) {
            // 如果当前菜单被设为了主菜单，就将其他所有菜单设为非主菜单
            setMainMenu(menuRequest.menuId!!)
        }
        result
    }

    /**
     * 获取菜单
     * @param menuId 菜单 ID
     */
    override suspend fun menu(menuId: Int): Menu? = dbQuery {
        Menus
            .selectAll()
            .where { Menus.menuId eq menuId }
            .map(::resultToMenu)
            .singleOrNull()
    }

    /**
     * 获取菜单
     * @param displayName 菜单名
     */
    override suspend fun menu(displayName: String): Menu? = dbQuery {
        Menus
            .selectAll()
            .where { Menus.displayName eq displayName }
            .map(::resultToMenu)
            .singleOrNull()
    }

    /**
     * 获取所有菜单
     */
    override suspend fun menus(): List<Menu> = dbQuery {
        Menus
            .selectAll()
            .orderBy(Menus.createTime, SortOrder.DESC)
            .map(::resultToMenu)
    }

    /**
     * 分页获取所有菜单
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun menus(page: Int, size: Int): Pager<Menu> {
        return Menus.startPage(page, size, ::resultToMenu) {
            selectAll().orderBy(Menus.createTime, SortOrder.DESC)
        }
    }

    /**
     * 添加菜单项
     * @param menuItem 菜单项请求数据类
     */
    override suspend fun addMenuItem(menuItem: MenuItemRequest): MenuItem? = dbQuery {
        val insertStatement = MenuItems.insert {
            it[displayName] = menuItem.displayName
            it[href] = menuItem.href
            it[target] = menuItem.target ?: MenuItemTarget.BLANK
            it[parentMenuId] = menuItem.parentMenuId
            it[parentMenuItemId] = menuItem.parentMenuItemId
            it[index] = menuItem.index
            it[createTime] = Date().time
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToMenuItem)
    }

    /**
     * 删除菜单项
     * @param menuItemIds 菜单项 ID 集合
     */
    override suspend fun deleteMenuItems(menuItemIds: List<Int>): Boolean = dbQuery {
        MenuItems.deleteWhere {
            menuItemId inList menuItemIds
        } > 0
    }

    /**
     * 修改菜单项
     * @param menuItem 菜单项请求数据类
     */
    override suspend fun updateMenuItem(menuItem: MenuItemRequest): Boolean = dbQuery {
        MenuItems.update({
            MenuItems.menuItemId eq menuItem.menuItemId!!
        }) {
            it[displayName] = menuItem.displayName
            it[href] = menuItem.href
            it[target] = menuItem.target ?: MenuItemTarget.BLANK
            it[parentMenuId] = menuItem.parentMenuId
            it[parentMenuItemId] = menuItem.parentMenuItemId
            it[index] = menuItem.index
            it[lastModifyTime] = Date().time
        } > 0
    }

    /**
     * 获取菜单项
     * @param menuItemId 菜单项 ID
     */
    override suspend fun menuItem(menuItemId: Int): MenuItem? = dbQuery {
        MenuItems
            .selectAll()
            .where { MenuItems.menuItemId eq menuItemId }
            .map(::resultToMenuItem)
            .singleOrNull()
    }

    /**
     * 根据菜单 ID 获取所有菜单项
     * @param menuId 菜单 ID
     */
    override suspend fun menuItems(menuId: Int): List<MenuItem> = dbQuery {
        MenuItems
            .selectAll()
            .where { MenuItems.parentMenuId eq menuId }
            .groupBy(MenuItems.parentMenuId)
            .orderBy(MenuItems.index, SortOrder.ASC)
            .map(::resultToMenuItem)
    }

    /**
     * 获取所有菜单项
     */
    override suspend fun menuItems(): List<MenuItem> = dbQuery {
        MenuItems
            .selectAll()
            .groupBy(MenuItems.parentMenuId)
            .orderBy(MenuItems.index, SortOrder.ASC)
            .map(::resultToMenuItem)
    }

    /**
     * 获取主菜单菜单项
     */
    override suspend fun mainMenuItems(): List<MenuItem> = dbQuery {
        MenuItems.join(Menus, JoinType.LEFT, additionalConstraint = {
            Menus.menuId eq MenuItems.parentMenuId
        })
            .selectAll()
            .where { Menus.isMain eq true }
            .groupBy(MenuItems.parentMenuId)
            .orderBy(MenuItems.index, SortOrder.ASC)
            .map(::resultToMenuItem)
    }


    /**
     * 获取菜单项数量
     */
    override suspend fun menuCount(): Long = dbQuery {
        Menus.selectAll().count()
    }

    /**
     * 将除了给定的菜单 ID 以外的主菜单设为非主菜单
     * @param menuId 菜单 ID
     */
    private fun setMainMenu(menuId: Int): Int {
        return Menus.update({
            Menus.menuId neq menuId
        }) {
            it[isMain] = false
        }
    }
}