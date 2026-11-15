package cc.loac.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

/**
 * 配置博客跨域
 * 环境变量 NOLA_CORS_ALLOWED_HOSTS 优先于配置文件，逗号分隔，可写完整 Origin
 */
fun Application.configureCORS() {
    val anyHost = System.getenv("NOLA_CORS_ANY_HOST")
        ?.toBooleanStrictOrNull()
        ?: environment.config.propertyOrNull("ktor.cors.anyHost")
            ?.getString()?.toBoolean() ?: false
    val allowedHosts = System.getenv("NOLA_CORS_ALLOWED_HOSTS")
        ?.split(',')
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?.takeIf { it.isNotEmpty() }
        ?: environment.config.propertyOrNull("ktor.cors.allowedHosts")
            ?.getList() ?: emptyList()

    install(CORS) {
        if (anyHost) {
            anyHost()
        } else {
            allowedHosts.forEach { origin ->
                val scheme = origin.substringBefore("://", "")
                if (scheme.isNotEmpty()) {
                    // 允许配置形如 https://blog.example.com 的完整 Origin
                    allowHost(
                        host = origin.substringAfter("://"),
                        schemes = listOf(scheme)
                    )
                } else {
                    allowHost(origin)
                }
            }
        }

        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Head)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)

        // 任意域名模式下不携带凭证，避免安全问题
        allowCredentials = !anyHost

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Authorization)
    }
}