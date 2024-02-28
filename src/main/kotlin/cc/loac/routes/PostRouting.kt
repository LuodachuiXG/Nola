package cc.loac.routes

import cc.loac.data.requests.AddPostRequest
import cc.loac.services.PostService
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val postService: PostService by inject(PostService::class.java)

/**
 * 文章，管理员路由
 */
fun Route.postAdminRouting() {
    route("/post") {
        /** 添加文章 **/
        post {
            val postRequest = call.receiveByDataClass<AddPostRequest>()
            call.respondSuccess(postService.addPost(postRequest) != null)
        }
    }
}

/**
 * 文章，API 路由
 */
fun Route.postApiRouting() {

}