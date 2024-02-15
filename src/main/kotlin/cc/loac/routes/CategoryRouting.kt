package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Category
import cc.loac.services.CategoryService
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val categoryService: CategoryService by inject(CategoryService::class.java)

/**
 * 分类，管理员路由
 */
fun Route.categoryAdminRouting() {
    route("/category") {
        // 分类操作需要登录
        authenticate {
            /** 添加分类 **/
            post {
                val category = call.receiveByDataClass<Category>()

                // 先判断分类名是否已经存在
                if (categoryService.category(category.displayName) != null) {
                    throw MyException("分类名 [${category.displayName}] 已经存在")
                }

                // 添加分类
                call.respondSuccess(categoryService.addCategory(category))
            }

            /** 删除分类 - 根据分类 ID **/
            delete {
                // 获取 ID 集合
                val ids = call.receiveByDataClass<List<Int>>()
                // 分类 ID 列表为空
                if (ids.isEmpty()) {
                    call.respondSuccess(true)
                }

                // 删除分类
                call.respondSuccess(categoryService.deleteCategories(ids))
            }

            /** 修改分类 **/
            put {
                val category = call.receiveByDataClass<Category>()
                call.respondSuccess(categoryService.updateCategory(category))
            }

            /** 获取所有分类 **/
            get {
                call.respondSuccess(categoryService.categories())
            }
        }
    }
}

/**
 * 分类，API 路由
 */
fun Route.categoryApiRouting() {
    route("/category") {
        /** 获取所有分类 **/
        get {
            call.respondSuccess(categoryService.categories())
        }
    }
}