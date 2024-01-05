package cc.loac.plugins

import cc.loac.sql.dao.impl.dao
import com.google.gson.Gson
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    routing {
        get {
            val gson = Gson()
            call.respondText(gson.toJson(dao.allArticles()))
        }
        post {
            val param = call.receiveParameters()
            param.getOrFail("")
        }
    }
}
