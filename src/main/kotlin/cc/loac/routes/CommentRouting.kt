package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Comment
import cc.loac.data.models.enums.CommentSort
import cc.loac.data.requests.CommentPassRequest
import cc.loac.data.requests.CommentRequest
import cc.loac.data.requests.CommentUpdateRequest
import cc.loac.extensions.isBoolean
import cc.loac.extensions.isEmail
import cc.loac.extensions.isEnum
import cc.loac.extensions.isInt
import cc.loac.plugins.LIMITER_ADD_COMMENT
import cc.loac.services.CommentService
import cc.loac.utils.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val commentService: CommentService by inject(CommentService::class.java)

// 操作记录中的评论内容长度限制
private const val OPERATION_COMMENT_MAX_LENGTH = 20

/**
 * 评论，管理员路由
 */
fun Route.commentAdminRouting() {
    authenticate {
        route("/comment") {
            /** 添加评论 **/
            post {
                val newComment = call.receiveByDataClass<CommentRequest> {
                    it.postId != -1L
                }
                call.respondSuccess(
                    commentService.addComment(
                        Comment(
                            postId = newComment.postId,
                            parentCommentId = newComment.parentCommentId,
                            replyCommentId = newComment.replyCommentId,
                            content = newComment.content,
                            site = newComment.site,
                            displayName = newComment.displayName,
                            email = newComment.email,
                            isPass = newComment.isPass
                        )
                    )?.also {
                        operate(
                            desc = "添加评论 ID: [${it.commentId}]，内容: [${
                                it.content.take(OPERATION_COMMENT_MAX_LENGTH)
                            }]",
                            call = call
                        )
                    }
                )
            }

            /** 删除评论 **/
            delete {
                val ids = call.receiveByDataClass<List<Long>>()
                call.respondSuccess(commentService.deleteCommentByIds(ids).also {
                    if (it) {
                        operate(
                            desc = "删除评论 [${ids.joinToString(",")}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改评论 **/
            put {
                val comment = call.receiveByDataClass<CommentUpdateRequest> {
                    it.commentId != -1L
                }
                call.respondSuccess(
                    commentService.updateComment(
                        Comment(
                            postId = -1,
                            commentId = comment.commentId,
                            content = comment.content,
                            site = comment.site,
                            displayName = comment.displayName,
                            email = comment.email,
                            isPass = comment.isPass,
                        )
                    ).also {
                        if (it) {
                            operate(
                                desc = "修改评论 ID: [${comment.commentId}]，内容: [${
                                    comment.content.take(OPERATION_COMMENT_MAX_LENGTH)
                                }]",
                                call = call
                            )
                        }
                    }
                )
            }

            /** 修改评论是否通过审核 **/
            put("/pass") {
                val request = call.receiveByDataClass<CommentPassRequest>()
                call.respondSuccess(
                    commentService.setCommentPass(request.ids, request.isPass).also {
                        if (it) {
                            operate(
                                desc = "评论通过审核 isPass: [${request.isPass}] [${request.ids.joinToString(",")}]",
                                call = call
                            )
                        }
                    }
                )
            }

            /** 获取评论 **/
            get {
                call.receivePageAndSize { page, size ->
                    // 可空文章 ID
                    val postId = call.receiveNullablePathParam("postId") {
                        it?.isInt()
                    }?.toLong()

                    // 可空评论 ID
                    val commentId = call.receiveNullablePathParam("commentId") {
                        it?.isInt()
                    }?.toLong()

                    // 可空父评论 ID
                    val parentId = call.receiveNullablePathParam("parentCommentId") {
                        it?.isInt()
                    }?.toLong()

                    // 可空邮箱
                    val email = call.receiveNullablePathParam("email") {
                        it?.isEmail()
                    }

                    // 可空名称
                    val displayName = call.receiveNullablePathParam("displayName")

                    // 可空是否通过审核
                    val isPass = call.receiveNullablePathParam("isPass") {
                        it?.isBoolean()
                    }?.toBoolean()

                    // 可空关键词
                    val key = call.receiveNullablePathParam("key")

                    // 可空的链接排序
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<CommentSort>()
                    }?.let { CommentSort.valueOf(it) }

                    // 可空是否树形结构
                    val tree = call.receiveNullablePathParam("tree") {
                        it?.isBoolean()
                    }?.toBoolean() ?: false

                    call.respondSuccess(
                        commentService.comments(
                            page = page,
                            size = size,
                            postId = postId,
                            commentId = commentId,
                            parentId = parentId,
                            email = email,
                            displayName = displayName,
                            isPass = isPass,
                            key = key,
                            sort = sort,
                            tree = tree
                        )
                    )
                }
            }
        }
    }
}


/**
 * 评论，API 路由
 */
fun Route.commentApiRouting() {
    route("/comment") {
        rateLimit(LIMITER_ADD_COMMENT) {
            /** 添加评论（每 30 秒仅能调用 2 次） **/
            post {
                val newComment = call.receiveByDataClass<CommentRequest> {
                    it.postId != -1L
                }

                call.respondSuccess(
                    commentService.addComment(
                        comment = Comment(
                            postId = newComment.postId,
                            parentCommentId = newComment.parentCommentId,
                            replyCommentId = newComment.replyCommentId,
                            content = newComment.content,
                            site = newComment.site,
                            displayName = newComment.displayName,
                            email = newComment.email,
                            isPass = false
                        ),
                        isApiRequest = true
                    )
                )
            }

        }

        /** 根据文章 ID 获取评论 **/
        get {
            call.receivePageAndSize { page, size ->
                // 可空参数，文章 ID
                val postId = call.receiveNullablePathParam("id") {
                    it?.isInt()
                }?.toLongOrNull()

                // 可空参数，文章别名
                val slug = call.receiveNullablePathParam("slug")

                if (postId == null && slug == null) {
                    throw MyException("文章 ID 和文章别名至少提供一个")
                }

                call.respondSuccess(
                    commentService.comments(
                        page = page,
                        size = size,
                        postId = postId,
                        slug = slug,
                        isPass = true,
                        tree = true
                    )
                )
            }
        }
    }
}