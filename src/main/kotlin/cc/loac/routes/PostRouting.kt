package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.*
import cc.loac.data.responses.ApiPostResponse
import cc.loac.data.responses.Pager
import cc.loac.plugins.LIMITER_ENCRYPT_POST
import cc.loac.services.PostService
import cc.loac.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.shortLiteral
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

                val post = postService.addPost(postRequest) ?: throw AddFailedException()
                // 返回文章
                call.respondSuccess(
                    postService.posts(listOf(post.postId), true)
                        .firstOrNull() ?: throw AddFailedException()
                )
            }

            /** 回收文章 - 根据文章 ID **/
            put("/recycle") {
                val postIds = call.receiveByDataClass<List<Int>>()
                call.respondSuccess(postService.updatePostStatusToDeleted(postIds))
            }

            /** 删除文章 - 根据文章 ID **/
            delete {
                val postIds = call.receiveByDataClass<List<Int>>()
                if (postIds.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(postService.deletePosts(postIds))
            }

            /** 修改文章 **/
            put {
                val postRequest = call.receiveByDataClass<PostRequest> {
                    // 如果 postId 等于 0 证明没传参，并且文章状态不能设置为已删除
                    it.postId != null && it.postId > 0 && it.status != PostStatus.DELETED
                }
                call.respondSuccess(postService.updatePost(postRequest))
            }

            /** 获取文章 **/
            get {
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

            /** 获取文章所有内容，包括正文和所有草稿 **/
            get("content/{postId}") {
                val postId = call.receiveIntPathParam("postId")
                call.respondSuccess(postService.postContents(postId))
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

            /** 修改文章正文 **/
            put("/publish") {
                val postContent = call.receiveByDataClass<PostContentRequest> {
                    // 如果 postId 等于 0，证明传参 null
                    it.postId > 0
                }
                call.respondSuccess(postService.updatePostContent(postContent))
            }

            /** 获取文章正文 **/
            get("/publish/{postId}") {
                val postId = call.receiveIntPathParam("postId")
                call.respondSuccess(
                    postService.postContent(postId)
                        ?: throw MyException("文章 [$postId] 不存在")
                )
            }

            /** 添加文章草稿 **/
            post("/content/draft") {
                val postDraft = call.receiveByDataClass<PostDraftRequest> {
                    // 如果 postId 等于 0，证明传参 null
                    it.postId > 0
                }
                call.respondSuccess(
                    postService.addPostDraft(
                        postDraft.postId,
                        postDraft.content, postDraft.draftName
                    )?.copy(
                        // 返回数据时把 content 置空
                        content = ""
                    ) ?: throw AddFailedException()
                )
            }

            /** 删除文章草稿 **/
            delete("/content/{postId}/draft") {
                val postId = call.receiveIntPathParam("postId")
                val draftNames = call.receiveByDataClass<List<String>>()
                if (draftNames.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(postService.deletePostContent(postId, PostContentStatus.DRAFT, draftNames))
            }

            /** 修改文章草稿 **/
            put("/content/draft") {
                val postDraft = call.receiveByDataClass<PostDraftRequest> {
                    // 如果 postId 等于 0，证明传参 null
                    it.postId > 0
                }
                val postContent = PostContentRequest(postDraft.postId, postDraft.content)
                call.respondSuccess(
                    postService.updatePostContent(
                        postContent,
                        PostContentStatus.DRAFT,
                        postDraft.draftName
                    )
                )
            }

            /** 修改文章草稿名 **/
            put("/content/draft/name") {
                val params = call.receiveByDataClass<PostDraftNameRequest> {
                    it.postId > 0
                }

                call.respondSuccess(postService.updatePostDraftName(params.postId, params.oldName, params.newName))
            }

            /** 将文章草稿转换为文章正文 **/
            put("/content/draft/publish") {
                val params = call.receiveByDataClass<PostDraft2ContentRequest> {
                    it.postId > 0
                }

                call.respondSuccess(
                    postService.updatePostDraft2Content(
                        params.postId,
                        params.draftName,
                        params.deleteContent,
                        params.contentName
                    )
                )
            }

            /** 获取文章草稿 **/
            get("/content/{postId}/draft/{draftName}") {
                val params = call.receivePathParams("postId", "draftName")
                // 判断文章 ID 是否为整数
                val postId = params["postId"]?.toIntOrNull() ?: throw ParamMismatchException()
                val draftName = params["draftName"]!!
                call.respondSuccess(
                    postService.postContent(postId, PostContentStatus.DRAFT, draftName)
                        ?: throw MyException("草稿 [$draftName] 不存在")
                )
            }
        }
    }
}

/**
 * 文章，API 路由
 */
fun Route.postApiRouting() {
    route("post") {
        /** 获取文章 **/
        get {
            call.receivePageAndSize { page, size ->
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

                call.respondSuccess(postService.apiPosts(page, size, key, tag, category))
            }
        }

        // 给文章添加速率限制器
        rateLimit(LIMITER_ENCRYPT_POST) {
            /** 获取文章内容 **/
            get("/content") {
                // 可空参数，文章 ID
                val postId = call.receiveNullablePathParam("postId") {
                    it?.isInt()
                }?.toInt()

                // 可空参数，文章别名
                val slug = call.receiveNullablePathParam("slug")

                // 可空参数，文章密码
                val password = call.receiveNullablePathParam("password")

                // 如果文章 ID 和别名都为空，返回 404
                if (postId == null && slug == null) return@get call.respond(HttpStatusCode.NotFound)

                // 如果文章返回空，也返回 404
                val postContent = postService.apiPostContent(postId, slug, password)
                    ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respondSuccess(postContent)
            }
        }
    }
}