package cc.loac.routes

import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.responses.respondSuccess
import cc.loac.data.sql.dao.impl.configDao
import cc.loac.utils.toJSON
import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * 配置信息后台路由
 */
fun Route.configAdminRouting() {
    route("/config") {

    }
}

/**
 * 配置信息博客接口
 */
fun Route.configApiRouting() {
    route("/config") {
        /**
         * 博客信息
         */
        get("/blog_info") {
            val result = configDao.config(ConfigKey.BLOG_INFO)
            if (result == null) {
                call.respondSuccess(null)
                return@get
            }
            call.respondSuccess(result.toJSON())
        }
    }
}