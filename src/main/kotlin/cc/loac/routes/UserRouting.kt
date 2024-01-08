package cc.loac.routes

import cc.loac.data.dao.impl.userDao
import cc.loac.data.models.User
import cc.loac.data.models.UserRole
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 用户路由
 */
fun Routing.userRouting() {
    route("/user") {
        get {
            if (userDao.allUsers().isEmpty()) {
                userDao.addUser(User(
                    name = "admin",
                    email = "admin@loac.cc",
                    displayName = "我不是罗大锤",
                    password = "123456",
                    role = UserRole.SUPER_ADMIN
                ))
            }

            call.respondText(Json.encodeToString(userDao.allUsers()))
        }
    }
}