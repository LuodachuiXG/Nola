package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.requests.PostRequest
import cc.loac.services.PostService
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.receivePageAndSize
import cc.loac.utils.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val postService: PostService by inject(PostService::class.java)

/**
 * 文章，管理员路由
 */
fun Route.postAdminRouting() {
    route("/post") {
        authenticate {
            /** 添加文章 **/
            post {
                val postRequest = call.receiveByDataClass<PostRequest> {
                    // 文章状态不能设置为已删除
                    it.status != PostStatus.DELETED
                }

                val post = postService.addPost(postRequest) ?: throw MyException("文章添加失败，请检查服务端日志")
                // 返回文章
                call.respondSuccess(postService.posts(listOf(post.postId), true))
            }

            /** 回收文章 - 根据文章 ID **/
            put("/recycle") {
                val postIds = call.receiveByDataClass<List<Int>>()
                call.respondSuccess(postService.updatePostStatusToDeleted(postIds))
            }

            /** 删除文章 - 根据文章 ID **/
            delete {
                val postIds = call.receiveByDataClass<List<Int>>()
                call.respondSuccess(postService.deletePosts(postIds))
            }

            /** 修改文章 **/
            put {
                val postRequest = call.receiveByDataClass<PostRequest> {
                    // 如果 postId 等于 0 证明没传参，并且文章状态不能设置为已删除
                    it.postId != 0 && it.status != PostStatus.DELETED
                }
                call.respondSuccess(postService.updatePost(postRequest))
            }

            /** 获取所有文章 **/
            get {
                call.respondSuccess(postService.posts())
            }

            /** 分页获取文章 **/
            get("/{page}/{size}") {
                call.receivePageAndSize { page, size ->
                    call.respondSuccess(postService.posts(page, size))
                }
            }
        }
    }
}

/**
 * 文章，API 路由
 */
fun Route.postApiRouting() {

}