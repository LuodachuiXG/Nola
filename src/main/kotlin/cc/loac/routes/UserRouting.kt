package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.responses.AuthResponse
import cc.loac.utils.respondSuccess
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.security.hashing.SaltedHash
import cc.loac.security.token.TokenClaim
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import cc.loac.services.UserService
import cc.loac.utils.receiveMapByName
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val hashingService = SHA256HashingService()
private val userService: UserService by inject(UserService::class.java)

/**
 * 用户路由
 */
fun Route.userRouting(
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    /** 用户相关接口 **/
    route("/user") {
        /** 验证登录是否过期 **/
        authenticate {
            get {
                call.respondSuccess(true)
            }
        }

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

            // 修改最后登录时间
            userService.updateLastLoginTime(user.userId)

            // 生成 Token
            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.userId.toString()
                )
            )

            // 封装登录响应数据类
            val authResponse = AuthResponse(
                username = user.username,
                email = user.email,
                displayName = user.displayName,
                description = user.description,
                createDate = user.createDate,
                lastLoginDate = user.lastLoginDate,
                avatar = user.avatar,
                token = token
            )

            // 返回登录响应数据类
            call.respondSuccess(authResponse)
        }
    }
}