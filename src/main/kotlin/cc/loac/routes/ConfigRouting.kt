package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.BlogInfo
import cc.loac.data.models.Config
import cc.loac.data.models.User
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.requests.BlogInfoRequest
import cc.loac.utils.respondSuccess
import cc.loac.services.ConfigService
import cc.loac.services.UserService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

private val configService: ConfigService by inject(ConfigService::class.java)
private val userService: UserService by inject(UserService::class.java)

/**
 * “配置”管理员路由
 */
fun Route.configAdminRouting() {
    route("/config") {
        /** 博客相关接口 **/
        route("/blog") {
            /** 初始化博客 **/
            post {
                // 先判断博客是否已经创建
                if (configService.blogInfo() != null) {
                    throw MyException("博客已经创建")
                }
                // 接收 "站点标题","站点副标题" 两个参数
                val receive = call.receiveMapByName("title".nonNull(), "subtitle".nullable())
                val blogInfo = BlogInfo(
                    title = receive["title"],
                    subtitle = receive["subtitle"],
                    createDate = Date().time
                )
                // 设置博客配置信息
                call.respondSuccess(configService.setBlogInfo(blogInfo))
            }

            /** 初始化管理员 **/
            post("/admin") {
                // 判断是否初始了博客
                if (configService.config(ConfigKey.BLOG_INFO) == null) {
                    throw MyException("请先初始化博客")
                }

                // 获取请求传参
                val receive = call.receiveMapByName(
                    "username",
                    "email",
                    "displayName",
                    "password"
                )

                // 封装用户类
                val user = User(
                    username = receive["username"]!!,
                    email = receive["email"]!!,
                    displayName = receive["displayName"]!!,
                    password = receive["password"]!!,
                    salt = ""
                )
                // 初始化管理员
                call.respondSuccess(userService.initAdmin(user))
            }

            authenticate {
                /** 修改博客信息 **/
                put {
                    // 先判断博客是否已经创建
                    val blogInfo = configService.blogInfo() ?: throw MyException("请先初始化博客")
                    val blogInfoRequest = call.receiveByDataClass<BlogInfoRequest>()
                    // 不修改博客的创建时间
                    call.respondSuccess(
                        configService.setBlogInfo(
                            blogInfo.copy(
                                title = blogInfoRequest.title,
                                subtitle = blogInfoRequest.subtitle,
                                logo = blogInfoRequest.logo,
                                favicon = blogInfoRequest.favicon
                            )
                        )
                    )
                }
            }
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
            /** 博客信息 **/
            get {
                // 获取博客信息
                val blogInfo = configService.blogInfo()
                // 检查是否存在管理员
                val user = userService.allUsers().firstOrNull()
                call.respondSuccess(blogInfo?.copy(blogger = user?.displayName))
            }
        }
    }
}