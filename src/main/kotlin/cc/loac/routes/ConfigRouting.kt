package cc.loac.routes

import cc.loac.data.responses.respondSuccess
import cc.loac.services.ConfigService
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

val configService: ConfigService by inject(ConfigService::class.java)

/**
 * “配置”管理员路由
 */
fun Route.configAdminRouting() {
    route("/config") {
        /** 博客相关接口 **/
        route("/blog") {
            adminBlog()
        }
    }
}

/**
 * “配置”API 路由
 */
fun Route.configApiRouting() {
    route("/config") {
        /** 博客相关接口 **/
        route("/blog") {
            apiBlog()
        }
    }
}

/**
 * 管理员 博客相关接口
 */
private fun Route.adminBlog() {
    post {

    }
}

/**
 * API 博客相关接口
 */
private fun Route.apiBlog() {
    /** 博客信息 **/
    get {
        call.respondSuccess(configService.blogInfo())
    }
}