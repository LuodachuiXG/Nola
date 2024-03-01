package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.services.PostService
import cc.loac.utils.*
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
                    // 可空文章状态
                    val status = call.receiveNullablePathParam("status") {
                        it?.isEnum<PostStatus>()
                    }?.let { PostStatus.valueOf(it) }

                    // 可空文章可见性
                    val visible = call.receiveNullablePathParam("visible") {
                        it?.isEnum<PostVisible>()
                    }?.let { PostVisible.valueOf(it) }

                    // 可空文章关键字
                    val key = call.receiveNullablePathParam("key")

                    // 可空文章标签
                    val tag = call.receiveNullablePathParam("tag") {
                        it?.isInt()
                    }?.toInt()

                    // 可空文章分类
                    val category = call.receiveNullablePathParam("category") {
                        it?.isInt()
                    }?.toInt()

                    // 可空文章排序
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<PostSort>()
                    }?.let { PostSort.valueOf(it) }


                    call.respondSuccess(postService.posts(page, size, status, visible, key, tag, category, sort))
                }
            }

            /** 获取文章 - 根据文章 ID **/
            get("/{postId}") {
                val postId = call.receiveIntPathParam("postId")
                call.respondSuccess(postService.posts(listOf(postId), true).firstOrNull())
            }

            /** 获取文章 - 根据文章别名 **/
            get("/slug/{slug}") {
                val slug = call.receivePathParam("slug")
                call.respondSuccess(postService.postBySlug(slug))
            }

            /** 修改文章内容 **/
            put("/content") {
                val postContent = call.receiveByDataClass<PostContentRequest> {
                    // 如果 postId 等于 0，证明传参 null
                    it.postId != 0
                }
                call.respondSuccess(postService.updatePostContent(postContent))
            }

            /** 获取文章内容 **/
            get("/content/{postId}") {
                val postId = call.receiveIntPathParam("postId")
                call.respondSuccess(postService.postContent(postId))
            }

            /** 获取文章草稿 **/
            get("/content/{postId}/draft/{draftName}") {
                val params = call.receivePathParams("postId", "draftName")
                // 判断文章 ID 是否为整数
                val postId = params["postId"]?.toIntOrNull() ?: throw ParamMismatchException()
                val draftName = params["draftName"]!!
                call.respondSuccess(postService.postContent(postId, PostContentStatus.DRAFT, draftName))
            }

        }
    }
}

/**
 * 文章，API 路由
 */
fun Route.postApiRouting() {

}