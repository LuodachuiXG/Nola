package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.responses.AuthResponse
import cc.loac.data.responses.respondFailure
import cc.loac.data.responses.respondSuccess
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.security.hashing.SaltedHash
import cc.loac.security.token.TokenClaim
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import cc.loac.utils.receiveMapByName
import io.ktor.server.application.*
import io.ktor.server.routing.*

private val hashingService = SHA256HashingService()

/**
 * 用户路由
 */
fun Route.userRouting(
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    /** 用户相关接口 **/
    route("/user") {
        /** 用户登录 **/
        post("/login") {
            val receive = call.receiveMapByName("username", "password")
            val user = userService.user(receive["username"]!!) ?: throw MyException("非法用户名或密码")
            // 验证密码合法性
            val isValidPassword = hashingService.verify(
                value = receive["password"]!!,
                saltedHash = SaltedHash(
                    salt = user.salt,
                    hash = user.password
                )
            )

            // 密码不合法
            if (!isValidPassword) {
                throw MyException("非法用户名或密码")
            }

            // 生成 Token
            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.userId.toString()
                )
            )

            // 返回 Token
            call.respondSuccess(
                AuthResponse(
                    token = token
                )
            )
        }
    }

//    authenticate {
//        get("/authenticate") {
//            call.respond(HttpStatusCode.OK)
//        }
//
//        get("/secret") {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//            call.respond(HttpStatusCode.OK, "Your userId is $userId")
//        }
//    }
}