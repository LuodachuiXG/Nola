package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.models.Category
import cc.loac.services.CategoryService
import cc.loac.utils.*
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
                // 添加分类
                call.respondSuccess(
                    categoryService.addCategory(category).also {
                        it?.let {
                            operate(
                                desc = "添加分类 [${it.displayName}]",
                                call = call
                            )
                        }
                    } ?: throw AddFailedException()
                )
            }

            /** 删除分类 - 根据分类 ID **/
            delete {
                // 获取 ID 集合
                val ids = call.receiveByDataClass<List<Long>>()
                // 分类 ID 列表为空
                if (ids.isEmpty()) call.respondSuccess(false)
                // 删除分类
                call.respondSuccess(categoryService.deleteCategories(ids).also {
                    if (it) {
                        operate(
                            desc = "删除分类，ID: [$ids]",
                            call = call
                        )
                    }
                })
            }

            /** 删除分类 - 根据分类别名 **/
            delete("/slug") {
                // 获取别名集合
                val slugs = call.receiveByDataClass<List<String>>()
                // 分类别名列表为空
                if (slugs.isEmpty()) call.respondSuccess(false)
                // 删除分类
                call.respondSuccess(categoryService.deleteCategoriesBySlugs(slugs).also {
                    if (it) {
                        operate(
                            desc = "删除分类，别名: [$slugs]",
                            call = call
                        )
                    }
                })
            }

            /** 修改分类 **/
            put {
                val category = call.receiveByDataClass<Category> { it.categoryId > 0 }
                // 修改分类
                call.respondSuccess(categoryService.updateCategory(category).also {
                    if (it) {
                        operate(
                            desc = "修改分类 [${category.displayName}]",
                            call = call
                        )
                    }
                })
            }

            /** 获取分类 - 根据分类 ID**/
            get("/{categoryId}") {
                val categoryId = call.receiveIntPathParam("categoryId").toLong()
                call.respondSuccess(categoryService.category(categoryId))
            }

            /** 获取分类 **/
            get {
                call.receivePageAndSize { page, size ->
                    call.respondSuccess(categoryService.categories(page, size))
                }
            }
        }
    }
}

/**
 * 分类，API 路由
 */
fun Route.categoryApiRouting() {
    route("/category") {
        /** 获取分类 **/
        get {
            call.receivePageAndSize { page, size ->
                call.respondSuccess(categoryService.categories(page, size))
            }
        }
    }
}