package cc.loac.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

/**
 * 配置博客跨域
 */
fun Application.configureCORS() {
    val anyHost = environment.config.propertyOrNull("ktor.cors.anyHost")
        ?.getString()?.toBoolean() ?: false
    val allowedHosts = environment.config.propertyOrNull("ktor.cors.allowedHosts")
        ?.getList() ?: emptyList()

    install(CORS) {
        if (anyHost) {
            anyHost()
        } else {
            allowedHosts.forEach { allowHost(it) }
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