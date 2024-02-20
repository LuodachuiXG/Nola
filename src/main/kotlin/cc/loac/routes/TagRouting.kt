package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.services.TagService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import cc.loac.data.models.Tag
import cc.loac.utils.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import org.koin.java.KoinJavaComponent.inject

private val tagService: TagService by inject(TagService::class.java)

/**
 * 标签，管理员路由
 */
fun Route.tagAdminRouting() {
    route("/tag") {
        // 标签操作需要登录
        authenticate {
            /** 添加标签 **/
            post {
                val tag = call.receiveByDataClass<Tag>()
                if (tag.displayName.isBlank() || tag.slug.isBlank()) {
                    // 标签名或别名为空
                    throw ParamMismatchException()
                }

                // 先判断标签别名是否已经存在
                if (tagService.tagBySlug(tag.slug) != null) {
                    throw MyException("标签别名 [${tag.slug}] 已经存在")
                }

                // 添加标签
                call.respondSuccess(tagService.addTag(tag))
            }

            /** 删除标签 - 根据标签 ID **/
            delete {
                // 获取 ID 集合
                val ids = call.receiveByDataClass<List<Int>>()
                // 标签 ID 列表为空
                if (ids.isEmpty()) {
                    call.respondSuccess(false)
                }

                // 删除标签
                call.respondSuccess(tagService.deleteTags(ids))
            }

            /** 删除标签 - 根据标签别名 **/
            delete("/slug") {
                // 获取别名集合
                val slugs = call.receiveByDataClass<List<String>>()
                // 标签别名列表为空
                if (slugs.isEmpty()) {
                    call.respondSuccess(false)
                }

                // 删除标签
                call.respondSuccess(tagService.deleteTagsBySlugs(slugs))
            }

            /** 修改标签 **/
            put {
                val tag = call.receiveByDataClass<Tag>()

                // 先判断标签别名是否已经存在，并且不是当前标签
                val t = tagService.tagBySlug(tag.slug)
                if (t != null && t.tagId != tag.tagId) {
                    throw MyException("标签别名 [${tag.slug}] 已经存在")
                }

                // 修改标签
                call.respondSuccess(tagService.updateTag(tag))
            }

            /** 获取所有标签 **/
            get {
                call.respondSuccess(tagService.tags())
            }

            /** 分页获取所有标签 **/
            get("/{page}/{size}") {
                call.receivePageAndSize { page, size ->
                    call.respondSuccess(tagService.tags(page, size))
                }
            }
        }
    }
}

/**
 * 标签，API 路由
 */
fun Route.tagApiRouting() {
    route("/tag") {
        /** 获取所有标签 **/
        get {
            call.respondSuccess(tagService.tags())
        }
    }
}