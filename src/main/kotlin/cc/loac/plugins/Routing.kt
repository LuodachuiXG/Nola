package cc.loac.plugins

import cc.loac.routes.*
import cc.loac.security.token.TokenConfig
import io.ktor.http.websocket.*
import io.ktor.network.sockets.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.consumeEach
import java.io.File
import java.net.SocketAddress
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * 配置博客路由
 * @param tokenConfig Token 令牌配置
 */
fun Application.configureRouting(
    tokenConfig: TokenConfig
) {
    routing {
        // 静态资源
        staticResources("/css", "/static/templates/css")
        staticResources("/js", "/static/templates/js")


        // 本地存储文件路由
        staticFiles("/upload", File(".nola/upload"))
        staticFiles("/backup", File(".nola/backup"))

        // 快捷接口
        quickRouting()

        // Vue 后台单页应用路由
        singlePageApplication {
            vue("static/console")
            applicationRoute = "/console"
            useResources = true
        }

        // 模板引擎路由
//        templateRouting()

        // Vue 前台单页应用路由
//        singlePageApplication {
//            vue("static")
//            applicationRoute = "/"
//            useResources = true
//        }

        /** 后台接口 **/
        route("/admin") {
            // user 用户路由
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
            // backup 备份路由
            backupRouting()
            // comment 评论路由
            commentAdminRouting()
            // operation 操作记录管理员路由
            operationAdminRouting()
            // overview 概览管理员路由
            overviewAdminRouting()
        }

        /** 博客接口 **/
        route("/api") {
            // user 用户 API 路由
            userApiRouting()
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
            // 评论 API 路由
            commentApiRouting()
            // overview 概览 API 路由
            overviewApiRouting()
        }
    }
}
