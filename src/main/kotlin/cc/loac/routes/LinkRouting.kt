package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.requests.LinkRequest
import cc.loac.services.LinkService
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

val linkService: LinkService by inject(LinkService::class.java)

/**
 * 友情链接，管理员路由
 */
fun Route.linkAdminRouting() {
    route("link") {
        /** 添加友情链接 **/
        post {
            val link = call.receiveByDataClass<LinkRequest>()
            call.respondSuccess(linkService.addLink(link) ?: throw AddFailedException())
        }

        /** 删除友情链接 **/
        delete {
            val ids = call.receiveByDataClass<List<Int>>()
            if (ids.isEmpty()) call.respondSuccess(false)
            call.respondSuccess(linkService.deleteLinks(ids))
        }
    }
}

/**
 * 友情链接，API 路由
 */
fun Route.linkApiRouting() {
    route("link") {

    }
}