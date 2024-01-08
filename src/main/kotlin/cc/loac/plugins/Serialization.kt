package cc.loac.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * 配置序列化插件
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}
