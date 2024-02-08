package cc.loac.plugins

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * 配置序列化插件
 */
fun Application.configureSerialization() {
    install(ContentNegotiation) {
        jackson {
            registerModules(JavaTimeModule())
        }
    }
}
