package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.services.TagService
import io.ktor.server.routing.*
import cc.loac.data.models.Tag
import cc.loac.utils.*
import io.ktor.server.auth.*
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
                // 添加标签
                call.respondSuccess(tagService.addTag(tag)?.also {
                    operate(
                        desc = "添加标签：[${tag.displayName}]",
                        call = call
                    )
                } ?: throw AddFailedException())
            }

            /** 删除标签 - 根据标签 ID **/
            delete {
                // 获取 ID 集合
                val ids = call.receiveByDataClass<List<Long>>()
                // 标签 ID 列表为空
                if (ids.isEmpty()) call.respondSuccess(false)
                // 删除标签
                call.respondSuccess(tagService.deleteTags(ids).also {
                    if (it && ids.isNotEmpty()) {
                        operate(
                            desc = "删除标签：[${ids.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 删除标签 - 根据标签别名 **/
            delete("/slug") {
                // 获取别名集合
                val slugs = call.receiveByDataClass<List<String>>()
                // 标签别名列表为空
                if (slugs.isEmpty()) call.respondSuccess(false)

                // 删除标签
                call.respondSuccess(tagService.deleteTagsBySlugs(slugs).also {
                    if (it && slugs.isNotEmpty()) {
                        operate(
                            desc = "删除标签：[${slugs.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改标签 **/
            put {
                val tag = call.receiveByDataClass<Tag> {
                    it.tagId > 0
                }
                // 修改标签
                call.respondSuccess(tagService.updateTag(tag).also {
                    if (it) {
                        operate(
                            desc = "修改标签：[${tag.displayName}]",
                            call = call
                        )
                    }
                })
            }

            /** 获取标签 - 根据标签 ID **/
            get("/{tagId}") {
                val tagId = call.receiveIntPathParam("tagId").toLong()
                call.respondSuccess(tagService.tag(tagId))
            }

            /** 获取标签 **/
            get {
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

        /** 获取标签 **/
        get {
            call.receivePageAndSize { page, size ->
                call.respondSuccess(tagService.tags(page, size))
            }
        }
    }
}