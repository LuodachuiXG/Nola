package cc.loac.routes

import cc.loac.services.CommentService
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val commentService: CommentService by inject(CommentService::class.java)

/**
 * 评论，管理员路由
 */
fun Route.commentAdminRouting() {
    route("/comment") {

    }
}


/**
 * 评论，API 路由
 */
fun Route.commentApiRouting() {
    route("/comment") {

    }
}