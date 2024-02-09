package cc.loac.plugins

import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * 配置序列化插件
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            registerKotlinModule()
        }
    }
}
