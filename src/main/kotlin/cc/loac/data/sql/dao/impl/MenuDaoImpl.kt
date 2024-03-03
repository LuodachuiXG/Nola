package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Config
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
        createTime = row[Menus.createTime]
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
        createTime = row[MenuItems.createTime]
    )


    /**
     * 添加菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun addMenu(menuRequest: MenuRequest): Menu? = dbQuery {
        Menus.insert {
            it[displayName] = menuRequest.displayName
            it[isMain] = menuRequest.isMain
            it[createTime] = Date().time
        }.resultedValues?.singleOrNull()?.let(::resultToMenu)
    }

    /**
     * 删除菜单
     * @param menuIds 菜单 ID 集合
     */
    override suspend fun deleteMenu(menuIds: List<Int>): Boolean = dbQuery {
        Menus.deleteWhere { menuId inList menuIds } > 0
    }

    /**
     * 修改菜单
     * @param menuRequest 菜单请求数据类
     */
    override suspend fun updateMenu(menuRequest: MenuRequest): Boolean = dbQuery {
        Menus.update({
            Menus.menuId eq menuRequest.menuId!!
        }) {
            it[displayName] = menuRequest.displayName
            it[isMain] = menuRequest.isMain
        } > 0
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
            it[createTime] = Date().time
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultToMenuItem)
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
}