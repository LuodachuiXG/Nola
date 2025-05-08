package cc.loac.routes

import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route


/**
 * 概述，管理员路由
 */
fun Route.overviewAdminRouting() {
    route("/overview") {
        authenticate {

        }
    }
}

/**
 * 概述，API 路由
 */
fun Route.overviewApiRouting() {
    route("/overview") {

    }
}