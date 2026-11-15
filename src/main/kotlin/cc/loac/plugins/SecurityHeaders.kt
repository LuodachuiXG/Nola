package cc.loac.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*

/**
 * 安全响应头插件
 */
private val SecurityHeaders = createApplicationPlugin("SecurityHeaders") {
    onCall { call ->
        call.response.headers.append("X-Content-Type-Options", "nosniff")
        call.response.headers.append("X-Frame-Options", "SAMEORIGIN")
        call.response.headers.append("Referrer-Policy", "no-referrer")
        call.response.headers.append("Permissions-Policy", "camera=(), microphone=(), geolocation=()")
        if (call.request.origin.scheme.equals("https", ignoreCase = true)) {
            call.response.headers.append(
                "Strict-Transport-Security",
                "max-age=31536000; includeSubDomains"
            )
        }
    }
}

/**
 * 配置安全响应头
 */
fun Application.configureSecurityHeaders() {
    install(SecurityHeaders)
}
