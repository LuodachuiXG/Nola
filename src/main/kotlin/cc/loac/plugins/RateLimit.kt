package cc.loac.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

/** 管理员登录速率限制器 */
val LIMITER_ADMIN_LOGIN = RateLimitName("LIMITER_ADMIN_LOGIN")
/** 加密文章获取速率限制器 **/
val LIMITER_ENCRYPT_POST = RateLimitName("LIMITER_ENCRYPT_POST")
/** 添加评论限速器 **/
val LIMITER_ADD_COMMENT = RateLimitName("LIMITER_COMMENT")

/**
 * 配置接口速率限制插件
 */
fun Application.configureRateLimit() {
    install(RateLimit) {
        // 全局限制器
        global {
            rateLimiter(limit = 300, refillPeriod = 60.seconds)
        }

        // 加密文章获取速率限制器
        register(LIMITER_ENCRYPT_POST) {
            rateLimiter(limit = 20, refillPeriod = 60.seconds)
        }

        // 注册管理员登录速率限制器
        register(LIMITER_ADMIN_LOGIN) {
            rateLimiter(limit = 10, refillPeriod = 60.seconds)
        }

        // 添加评论限速器
        register(LIMITER_ADD_COMMENT) {
            rateLimiter(limit = 2, refillPeriod = 30.seconds)
        }
    }
}