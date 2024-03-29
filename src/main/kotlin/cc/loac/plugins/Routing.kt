package cc.loac.plugins

import cc.loac.routes.*
import cc.loac.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import java.io.File

/**
 * 配置博客路由
 * @param tokenConfig Token 令牌配置
 */
fun Application.configureRouting(
    tokenConfig: TokenConfig
) {
    routing {
        // 博客控制台静态资源
        staticResources("/console", "/static/console")
        staticResources("/css", "/static/templates/css")
        staticResources("/js", "/static/templates/js")

        // 本地存储文件路由
        staticFiles("/upload", File(".nola/upload"))

        route("/") {
            // 博客页面路由
            blogRouting()
        }

        /** 后台接口 **/
        route("/admin") {
            // user 用户相关路由
            userRouting(tokenConfig)
            // config 博客配置管理员路由
            configAdminRouting()
            // tag 标签管理员路由
            tagAdminRouting()
            // category 分类管理员路由
            categoryAdminRouting()
            // post 文章管理员路由
            postAdminRouting()
            // link 友情链接管理员路由
            linkAdminRouting()
            // menu 菜单管理员路由
            menuAdminRouting()
            // diary 日记管理员路由
            diaryAdminRouting()
            // file 文件管理员路由
            fileAdminRouting()
        }

        /** 博客接口 **/
        route("/api") {
            // config 博客配置 API 路由
            configApiRouting()
            // tag 标签 API 路由
            tagApiRouting()
            // category 分类 API 路由
            categoryApiRouting()
            // post 文章 API 路由
            postApiRouting()
            // link 友情链接 API 路由
            linkApiRouting()
            // menu 菜单 API 路由
            menuApiRouting()
            // diary 日记 API 路由
            diaryApiRouting()
        }
    }
}
