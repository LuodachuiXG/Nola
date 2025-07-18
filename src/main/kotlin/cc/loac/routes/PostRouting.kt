package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.exceptions.MyException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.models.Post
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.*
import cc.loac.data.responses.toApiPostResponse
import cc.loac.extensions.isEnum
import cc.loac.extensions.isInt
import cc.loac.plugins.LIMITER_ENCRYPT_POST
import cc.loac.services.PostService
import cc.loac.utils.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
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

                val post = postService.addPost(postRequest) ?: throw AddFailedException()
                // 返回文章
                call.respondSuccess(
                    postService.posts(listOf(post.postId), true)
                        .firstOrNull()?.also {
                            operate(
                                desc = "添加文章 [${it.title}]",
                                call = call
                            )
                        } ?: throw AddFailedException()
                )
            }

            /** 回收文章 - 根据文章 ID **/
            put("/recycle") {
                val postIds = call.receiveByDataClass<List<Long>>()
                call.respondSuccess(postService.updatePostStatusToDeleted(postIds).also {
                    if (it) {
                        operate(
                            desc = "回收文章 [${postIds.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 恢复文章 - 根据文章 ID **/
            put("/restore/{status}") {
                val postIds = call.receiveByDataClass<List<Long>>()
                val status = call.receivePathParam("status")
                // 判断是否是合法的枚举
                val statusEnum = kotlin.runCatching {
                    PostStatus.valueOf(status)
                }.getOrNull() ?: throw ParamMismatchException()
                // 状态不能为已删除
                if (statusEnum == PostStatus.DELETED) throw ParamMismatchException()

                call.respondSuccess(postService.updatePostStatusTo(postIds, statusEnum).also {
                    if (it) {
                        operate(
                            desc = "恢复文章 [${postIds.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 删除文章 - 根据文章 ID **/
            delete {
                val postIds = call.receiveByDataClass<List<Long>>()
                if (postIds.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(postService.deletePosts(postIds).also {
                    if (postIds.isNotEmpty()) {
                        operate(
                            desc = "删除文章 [${postIds.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改文章 **/
            put {
                val postRequest = call.receiveByDataClass<PostRequest> {
                    // 如果 postId 等于 0 证明没传参
                    it.postId != null && it.postId > 0
                }
                // 文章设为加密，但是没有提供密码
                if (postRequest.encrypted == true && postRequest.password.isNullOrEmpty()) {
                    throw MyException("文章设为加密需要提供密码")
                }
                call.respondSuccess(postService.updatePost(postRequest).also {
                    if (it) {
                        operate(
                            desc = "修改文章 [${postRequest.title}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改文章状态，如：文章状态、可见性、置顶 **/
            put("/status") {
                val postStatusRequest = call.receiveByDataClass<PostStatusRequest> {
                    it.postId != 0L
                }
                call.respondSuccess(postService.updatePostStatus(postStatusRequest).also {
                    if (it) {
                        operate(
                            desc = "修改文章状态 [${postStatusRequest.postId}]，新状态: [${postStatusRequest.status}]",
                            call = call
                        )
                    }
                })
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
                    }?.toLong()

                    // 可空文章分类
                    val category = call.receiveNullablePathParam("category") {
                        it?.isInt()
                    }?.toLong()

                    // 可空文章排序
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<PostSort>()
                    }?.let { PostSort.valueOf(it) }


                    call.respondSuccess(postService.posts(page, size, status, visible, key, tag, category, sort))
                }
            }

            /** 获取文章所有内容，包括正文和所有草稿 **/
            get("content/{postId}") {
                val postId = call.receiveIntPathParam("postId").toLong()
                call.respondSuccess(postService.postContents(postId))
            }

            /** 获取文章 - 根据文章 ID **/
            get("/{postId}") {
                val postId = call.receiveIntPathParam("postId").toLong()
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
                val postId = call.receiveIntPathParam("postId").toLong()
                call.respondSuccess(
                    postService.postContent(postId)
                        ?: throw MyException("文章 [$postId] 不存在")
                )
            }

            /** 添加文章草稿 **/
            post("/draft") {
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
                    )?.also {
                        operate(
                            desc = "添加文章草稿，文章 ID: [${postDraft.postId}]，草稿名: [${postDraft.draftName}]",
                            call = call
                        )
                    } ?: throw AddFailedException()
                )
            }

            /** 删除文章草稿 **/
            delete("/draft/{postId}") {
                val postId = call.receiveIntPathParam("postId").toLong()
                val draftNames = call.receiveByDataClass<List<String>>()
                if (draftNames.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(postService.deletePostContent(postId, PostContentStatus.DRAFT, draftNames).also {
                    if (it) {
                        operate(
                            desc = "删除文章草稿，文章 ID: [${postId}]，草稿名: [${draftNames}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改文章草稿 **/
            put("/draft") {
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
                    ).also {
                        if (it) {
                            operate(
                                desc = "修改文章草稿，文章 ID: [${postDraft.postId}]，草稿名: [${postDraft.draftName}]",
                                call = call
                            )
                        }
                    }
                )
            }

            /** 修改文章草稿名 **/
            put("/draft/name") {
                val params = call.receiveByDataClass<PostDraftNameRequest> {
                    it.postId > 0
                }

                call.respondSuccess(
                    postService.updatePostDraftName(params.postId, params.oldName, params.newName).also {
                        if (it) {
                            operate(
                                desc = "修改文章草稿名，文章 ID: [${params.postId}]，旧草稿名: [${params.oldName}]，新草稿名: [${params.newName}]",
                                call = call
                            )
                        }
                    })
            }

            /** 将文章草稿转换为文章正文 **/
            put("/draft/publish") {
                val params = call.receiveByDataClass<PostDraft2ContentRequest> {
                    it.postId > 0
                }

                call.respondSuccess(
                    postService.updatePostDraft2Content(
                        params.postId,
                        params.draftName,
                        params.deleteContent,
                        params.contentName
                    ).also {
                        if (it) {
                            operate(
                                desc = "将文章草稿转换为文章正文，文章 ID: [${params.postId}]，草稿名: [${params.draftName}]",
                                call = call
                            )
                        }
                    }
                )
            }

            /** 获取文章草稿 **/
            get("/{postId}/draft/{draftName}") {
                val params = call.receivePathParams("postId", "draftName")
                // 判断文章 ID 是否为整数
                val postId = params["postId"]?.toLongOrNull() ?: throw ParamMismatchException()
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
        /** 分页获取文章 **/
        get {
            call.receivePageAndSize { page, size ->
                // 可空文章关键字
                val key = call.receiveNullablePathParam("key")

                // 可空文章标签 ID
                val tagId = call.receiveNullablePathParam("tagId") {
                    it?.isInt()
                }?.toLong()

                // 可空文章分类 ID
                val categoryId = call.receiveNullablePathParam("categoryId") {
                    it?.isInt()
                }?.toLong()
                // 可空标签名或别名
                val tag = call.receiveNullablePathParam("tag")

                // 可空分类名或别名
                val category = call.receiveNullablePathParam("category")

                call.respondSuccess(postService.apiPosts(page, size, key, tagId, categoryId, tag, category))
            }
        }

        /** 获取文章 - 根据文章 ID **/
        get("/{postId}") {
            val postId = call.receiveIntPathParam("postId").toLong()
            val post = postService.posts(listOf(postId), true).firstOrNull()
            // 如果文章不存在，或者文章未发布，则返回 404
            if (post == null || post.status != PostStatus.PUBLISHED) {
                return@get call.respondFailure(HttpStatusCode.NotFound.description, HttpStatusCode.NotFound)
            }
            call.respondSuccess(post.toApiPostResponse())
        }

        /** 获取文章 - 根据文章别名 **/
        get("/slug/{slug}") {
            val slug = call.receivePathParam("slug")
            val post = postService.postBySlug(slug)
            // 如果文章不存在，或者文章不可见，或者文章未发布，则返回 404
            if (post == null || post.status != PostStatus.PUBLISHED) {
                return@get call.respondFailure(HttpStatusCode.NotFound.description, HttpStatusCode.NotFound)
            }
            call.respondSuccess(post.toApiPostResponse())
        }

        // 给文章添加速率限制器
        rateLimit(LIMITER_ENCRYPT_POST) {
            /** 获取文章内容 **/
            get("/content") {
                // 可空参数，文章 ID
                val postId = call.receiveNullablePathParam("id") {
                    it?.isInt()
                }?.toLongOrNull()

                // 可空参数，文章别名
                val slug = call.receiveNullablePathParam("slug")

                // 可空参数，文章密码
                val password = call.receiveNullablePathParam("password")

                // 如果文章 ID 和别名都为空
                if (postId == null && slug == null) throw MyException("文章不存在或不可见")

                // 如果文章返回空
                val postContent = postService.apiPostContent(postId, slug, password)
                    ?: throw MyException("文章不存在或不可见")
                call.respondSuccess(postContent)
            }
        }
    }
}