package cc.loac.routes

import cc.loac.services.ConfigService
import cc.loac.utils.respondFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val configService: ConfigService by inject(ConfigService::class.java)

/**
 * 快捷接口，API 路由
 */
fun Route.quickRouting() {
    /** 重定向到博客 LOGO */
    get("/logo") {
        val blogInfo = configService.blogInfo() ?:
            return@get call.respondFailure(HttpStatusCode.NotFound)
        blogInfo.logo ?: return@get call.respondFailure(HttpStatusCode.NotFound)
        call.respondRedirect(blogInfo.logo)
    }

    /** 重定向到博客 Favicon */
    get("/favicon") {
        val blogInfo = configService.blogInfo() ?:
        return@get call.respondFailure(HttpStatusCode.NotFound)
        blogInfo.favicon ?: return@get call.respondFailure(HttpStatusCode.NotFound)
        call.respondRedirect(blogInfo.favicon)
    }
}