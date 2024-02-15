package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.services.TagService
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.routing.*
import cc.loac.data.models.Tag
import cc.loac.utils.receiveByDataClass
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

                // 先判断标签是否已经存在
                if (tagService.tag(tag.displayName) != null) {
                    throw MyException("标签名 [${tag.displayName}] 已经存在")
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
                    call.respondSuccess(true)
                }

                // 删除标签
                call.respondSuccess(tagService.deleteTags(ids))
            }

            /** 修改标签 **/
            put {
                val tag = call.receiveByDataClass<Tag>()
                call.respondSuccess(tagService.updateTag(tag))
            }

            /** 获取所有标签 **/
            get {
                call.respondSuccess(tagService.tags())
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