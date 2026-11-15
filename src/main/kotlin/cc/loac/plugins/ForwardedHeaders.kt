package cc.loac.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*

/**
 * 可信代理头配置
 */
class TrustedProxyHeadersConfig {
    /** 可信代理地址列表 */
    var trustedProxies: List<String> = emptyList()
}

/**
 * 仅当请求直接来自可信代理时，才采纳 X-Forwarded-For / X-Real-IP
 * 防止客户端直接伪造转发头绕过限流或污染日志
 */
val TrustedProxyHeaders = createApplicationPlugin(
    name = "TrustedProxyHeaders",
    createConfiguration = ::TrustedProxyHeadersConfig
) {
    onCall { call ->
        val directClient = call.request.origin.remoteHost
        if (directClient !in pluginConfig.trustedProxies) return@onCall

        val forwardedValues = call.request.headers[HttpHeaders.XForwardedFor]
            ?.split(',')
            ?.mapNotNull(::normalizeIp)
            ?.takeIf { it.isNotEmpty() }
        val clientIp = forwardedValues
            ?.asReversed()
            ?.firstOrNull { it !in pluginConfig.trustedProxies }
            ?: forwardedValues?.firstOrNull()
            ?: call.request.headers["X-Real-IP"]?.let(::normalizeIp)
            ?: return@onCall

        call.mutableOriginConnectionPoint.apply {
            remoteHost = clientIp
            remoteAddress = clientIp
            // 仅在可信代理请求中采纳协议头，用于 HTTPS/HSTS 判断
            val scheme = call.request.headers["X-Forwarded-Proto"]
                ?.substringBefore(',')
                ?.trim()
                ?.lowercase()
            if (scheme == "http" || scheme == "https") {
                this.scheme = scheme
            }
        }
    }
}

/**
 * 配置可信反向代理
 * 通过环境变量 NOLA_TRUSTED_PROXIES 配置，逗号分隔；默认信任本机回环地址
 */
fun Application.configureTrustedProxies() {
    val trustedProxies = System.getenv("NOLA_TRUSTED_PROXIES")
        ?.split(',')
        ?.mapNotNull(::normalizeIp)
        ?.takeIf { it.isNotEmpty() }
        ?: listOf("127.0.0.1", "::1", "0:0:0:0:0:0:0:1")

    install(TrustedProxyHeaders) {
        this.trustedProxies = trustedProxies
    }
}

/**
 * 简单校验并规范化 IP 地址，拒绝包含控制字符或非法内容的转发头
 */
private fun normalizeIp(value: String): String? {
    var ip = value.trim()
    ip = when {
        ip.startsWith("[") -> ip.substringAfter("[").substringBefore("]")
        ip.count { it == ':' } == 1 -> ip.substringBefore(':')
        else -> ip
    }
    if (ip.isEmpty() || ip.length > 64) return null
    if (!ip.matches(Regex("[0-9a-fA-F:.]+"))) return null
    return ip
}
