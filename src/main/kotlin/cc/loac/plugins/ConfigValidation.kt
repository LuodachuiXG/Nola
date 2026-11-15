package cc.loac.plugins

import io.ktor.server.application.*
import io.ktor.server.config.*

/**
 * 生产环境配置校验
 * 开发模式允许默认值，生产模式必须显式提供安全的数据库密码和 JWT 密钥
 */
fun Application.validateProductionConfiguration() {
    val development = environment.config.propertyOrNull("ktor.development")
        ?.getString()?.toBoolean() ?: false
    if (development) return

    val errors = mutableListOf<String>()

    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString().orEmpty()
    if (jwtSecret.length < 32 || jwtSecret == "jwt-secret") {
        errors.add("jwt.secret 必须设置为至少 32 位的随机字符串（可通过 NOLA_JWT_SECRET 提供）")
    }

    val dbPassword = environment.config.propertyOrNull("ktor.storage.password")?.getString().orEmpty()
    if (dbPassword.isBlank() || dbPassword == "123456") {
        errors.add("数据库密码不能为空或默认值（可通过 NOLA_DB_PASSWORD 提供）")
    }

    val anyHost = environment.config.propertyOrNull("ktor.cors.anyHost")
        ?.getString()?.toBoolean() ?: false
    if (anyHost) {
        errors.add("生产环境必须关闭 ktor.cors.anyHost，并通过 allowedHosts 或 NOLA_CORS_ALLOWED_HOSTS 指定前端域名")
    } else {
        val envAllowedHosts = System.getenv("NOLA_CORS_ALLOWED_HOSTS")
            ?.split(',')
            ?.any { it.isNotBlank() } == true
        val configAllowedHosts = environment.config
            .propertyOrNull("ktor.cors.allowedHosts")
            ?.getList()
            ?.any { it.isNotBlank() } == true
        if (!envAllowedHosts && !configAllowedHosts) {
            errors.add("生产环境必须配置前端域名（ktor.cors.allowedHosts 或 NOLA_CORS_ALLOWED_HOSTS）")
        }
    }

    if (errors.isNotEmpty()) {
        throw IllegalStateException(
            "生产配置不安全，已拒绝启动：\n" + errors.joinToString("\n") { "- $it" }
        )
    }
}
