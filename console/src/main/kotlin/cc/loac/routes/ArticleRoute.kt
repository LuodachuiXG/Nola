package cc.loac.routes

import cc.loac.data.dao.impl.dao
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Routing.articleRoute() {
    route("/article") {
        get {
            call.respondText(Json.encodeToString(dao.allArticles()))
        }
    }
}