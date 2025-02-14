package cc.loac.routes

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.enums.ConfigKey
import cc.loac.extensions.jsonToClass
import cc.loac.services.ConfigService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import org.koin.java.KoinJavaComponent

private val configService: ConfigService by KoinJavaComponent.inject(ConfigService::class.java)

/**
 * 博客页面路由
 */
fun Route.blogRouting() {
    /** 博客主页 **/
    get {
        val blogInfo = configService.config(ConfigKey.BLOG_INFO)?.jsonToClass<BlogInfo>()
        // 如果博客信息为空，跳转到管理页面
        blogInfo ?: return@get call.respondRedirect("/console")
        call.respond(ThymeleafContent("index", mapOf("blogInfo" to blogInfo)))
    }
}