package cc.loac.routes

import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.data.requests.UserInfoRequest
import cc.loac.data.responses.toBloggerResponse
import cc.loac.data.responses.toUserInfoResponse
import cc.loac.plugins.LIMITER_ADMIN_LOGIN
import cc.loac.security.token.TokenConfig
import cc.loac.services.UserService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val userService: UserService by inject(UserService::class.java)

/**
 * 用户路由
 * @param tokenConfig Token 令牌配置
 */
fun Route.userRouting(
    tokenConfig: TokenConfig
) {
    /** 用户相关接口 **/
    route("/user") {
        /** 验证登录是否过期 **/
        authenticate {
            get("/validate") {
                call.respondSuccess(true)
            }

            /** 获取登录用户信息 **/
            get {
                val userId = call.getTokenClaim(TokenClaimEnum.USER_ID)?.value!!
                call.respondSuccess(userService.user(userId.toInt())?.toUserInfoResponse())
            }

            /** 修改登录用户的信息 **/
            put {
                val userId = call.getTokenClaim(TokenClaimEnum.USER_ID)?.value!!
                val userInfo = call.receiveByDataClass<UserInfoRequest>()
                call.respondSuccess(userService.updateUser(userId.toInt(), userInfo))
            }

            /** 修改密码 **/
            put("/password") {
                val userId = call.getTokenClaim(TokenClaimEnum.USER_ID)?.value!!
                val newPassword = call.receiveMapByName("password")["password"]!!
                call.respondSuccess(userService.updatePassword(userId.toInt(), newPassword))
            }
        }

        // 对用户登录接口进行限流
        rateLimit(LIMITER_ADMIN_LOGIN) {
            /** 用户登录 **/
            post("/login") {
                val receive = call.receiveMapByName("username", "password")
                // 返回登录响应数据类
                call.respondSuccess(userService.login(tokenConfig, receive["username"]!!, receive["password"]!!))
            }
        }
    }
}


/**
 * 用户 API 路由
 */
fun Route.userApiRouting() {
    route("/blogger") {
        /** 获取博主信息 **/
        get {
            call.respondSuccess(userService.allUsers().first().toBloggerResponse())
        }
    }
}