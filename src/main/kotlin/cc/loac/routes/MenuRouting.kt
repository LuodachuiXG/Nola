package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.services.MenuService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import kotlinx.css.tr
import org.koin.java.KoinJavaComponent.inject

private val menuService: MenuService by inject(MenuService::class.java)

/**
 * 菜单，管理员路由
 */
fun Route.menuAdminRouting() {
    route("/menu") {
        authenticate {
            /** 添加菜单 **/
            post {
                val menu = call.receiveByDataClass<MenuRequest>()
                call.respondSuccess(menuService.addMenu(menu) ?: throw AddFailedException())
            }

            /** 删除菜单 **/
            delete {
                val menuIds = call.receiveByDataClass<List<Int>>()
                call.respondSuccess(menuService.deleteMenu(menuIds))
            }

            /** 修改菜单 **/
            put {
                val menu = call.receiveByDataClass<MenuRequest> {
                    it.menuId != null
                }
                call.respondSuccess(menuService.updateMenu(menu))
            }

            /** 获取菜单 **/
            get {
                call.receivePageAndSize { page, size ->
                    call.respondSuccess(menuService.menus(page, size))
                }
            }

            /** 添加菜单项 **/
            post("/item") {
                val menuItem = call.receiveByDataClass<MenuItemRequest> {
                    it.parentMenuId > 0
                }
                call.respondSuccess(
                    menuService.addMenuItem(menuItem)
                        ?: throw AddFailedException()
                )
            }

            /** 删除菜单项 **/
            delete("/item") {
                val menuItemIds = call.receiveByDataClass<List<Int>>()
                call.respondSuccess(menuService.deleteMenus(menuItemIds))
            }

            /** 修改菜单项 **/
            put("/item") {
                val menuItem = call.receiveByDataClass<MenuItemRequest> {
                    it.menuItemId != null && it.parentMenuId > 0
                }
                call.respondSuccess(menuService.updateMenuItem(menuItem))
            }

            /** 获取菜单项 **/
            get("/item/{menuId}") {
                // 获取菜单 ID
                val menuId = call.receiveIntPathParam("menuId")
                // 是否构建树，默认为 true，构建菜单项树
                val isBuildTree =
                    call.receiveNullableBooleanPathParam("tree", true)
                call.respondSuccess(menuService.menuItems(menuId, isBuildTree))
            }
        }
    }
}

/**
 * 菜单，API 路由
 */
fun Route.menuApiRouting() {
    route("menu") {
        /** 获取主菜单 **/
        get {
            // 是否构建树，默认为 true，构建菜单项树
            val isBuildTree = call.receiveNullableBooleanPathParam("tree", true)
            call.respondSuccess(menuService.mainMenu(isBuildTree))
        }
    }
}