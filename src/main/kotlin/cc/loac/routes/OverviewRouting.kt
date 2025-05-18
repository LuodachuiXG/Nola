package cc.loac.routes

import cc.loac.services.OverviewService
import cc.loac.utils.getUser
import cc.loac.utils.respondSuccess
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.java.KoinJavaComponent.inject


private val overviewService: OverviewService by inject(OverviewService::class.java)

/**
 * 概览，管理员路由
 */
fun Route.overviewAdminRouting() {
    route("/overview") {
        authenticate {
            get {
                val userId = call.getUser().first
                call.respondSuccess(
                    overviewService.getOverview(
                        userId = userId
                    )
                )
            }
        }
    }
}

/**
 * 概览，API 路由
 */
fun Route.overviewApiRouting() {
    route("/overview") {

    }
}