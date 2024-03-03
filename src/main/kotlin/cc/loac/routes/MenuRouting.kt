package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.services.MenuService
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.receivePageAndSize
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
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
                val menuItem = call.receiveByDataClass<MenuItemRequest>()
                call.respondSuccess(menuService.addMenuItem(menuItem)
                    ?: throw AddFailedException())
            }
        }
    }
}

/**
 * 菜单，API 路由
 */
fun Route.menuApiRouting() {
    route("menu") {

    }
}