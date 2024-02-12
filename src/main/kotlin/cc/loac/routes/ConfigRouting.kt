package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.BlogInfo
import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.responses.respondFailure
import cc.loac.data.responses.respondSuccess
import cc.loac.data.sql.tables.Configs
import cc.loac.services.ConfigService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import kotlinx.css.sub
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.exposedLogger
import org.koin.java.KoinJavaComponent.inject
import java.util.*

val configService: ConfigService by inject(ConfigService::class.java)

/**
 * “配置”管理员路由
 */
fun Route.configAdminRouting() {
    route("/config") {
        /** 博客相关接口 **/
        route("/blog") {
            /** 创建博客 **/
            post {
                // 先判断博客是否已经创建
                if (configService.config(ConfigKey.BLOG_INFO) != null) {
                    throw MyException("博客已经创建")
                }

                // 接收 "站点标题","站点副标题" 两个参数
                val receive = call.receiveMapByName("title", "subtitle")
                val blogInfo =
                    BlogInfo(title = receive["title"], subtitle = receive["subtitle"], createDate = Date().time)
                val config = Config(key = ConfigKey.BLOG_INFO, value = blogInfo.toJSONString())
                if (configService.addConfig(config) != null) {
                    call.respondSuccess(blogInfo)
                } else {
                    call.respondFailure("初始化失败，详情日志")
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
                call.respondSuccess(configService.config(ConfigKey.BLOG_INFO))
            }
        }
    }
}