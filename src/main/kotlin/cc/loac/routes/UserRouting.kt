package cc.loac.routes

import cc.loac.data.dao.impl.userDao
import cc.loac.data.exceptions.MyException
import cc.loac.data.models.User
import cc.loac.data.requests.AuthRequest
import cc.loac.data.responses.AuthResponse
import cc.loac.data.responses.respondSuccess
import cc.loac.security.hashing.HashingService
import cc.loac.security.hashing.SaltedHash
import cc.loac.security.token.TokenClaim
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * 用户路由
 */
fun Routing.userRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    route("/user") {
        post("/login") {
            val request = runCatching {
                call.receiveNullable<AuthRequest>()
            }.getOrNull() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val user = userDao.user(request.username)
            if (user == null) {
                call.respond(HttpStatusCode.Conflict, "非法用户名或密码")
                return@post
            }

            val isValidPassword = hashingService.verify(
                value = request.password,
                saltedHash = SaltedHash(
                    salt = user.salt,
                    hash = user.password
                )
            )

            if (!isValidPassword) {
                call.respond(HttpStatusCode.Conflict, "非法用户名或密码")
                return@post
            }

            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.toString()
                )
            )

            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(
                    token = token
                )
            )

        }

        get {
            call.respondSuccess(userDao.allUsers())
        }


        post {
            val request = runCatching {
                call.receiveNullable<AuthRequest>()
            }.getOrNull() ?: run {
                throw MyException("参数不匹配")
            }

            val areFieldsBlank = request.username.isBlank() ||
                    request.password.isBlank()
            val isPwTooShort = request.password.length < 8
            if (areFieldsBlank || isPwTooShort) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            val saltHash = hashingService.generatedSaltedHash(request.password)
            val user = User(
                name = request.username,
                password = saltHash.hash,
                salt = saltHash.salt,
                email = "admin@loac.cc",
                displayName = "Loac",
            )

            val wasAcknowledged = userDao.addUser(user)
            if (wasAcknowledged == null) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK)

        }
    }

    authenticate {
        get("/authenticate") {
            call.respond(HttpStatusCode.OK)
        }

        get("/secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}