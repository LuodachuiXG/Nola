package cc.loac.routes

import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.plugins.LIMITER_ADMIN_LOGIN
import cc.loac.utils.respondSuccess
import cc.loac.security.token.TokenConfig
import cc.loac.services.UserService
import cc.loac.utils.error
import cc.loac.utils.getTokenClaim
import cc.loac.utils.receiveMapByName
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
                call.getTokenClaim(TokenClaimEnum.USER_ID)?.value?.error()
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