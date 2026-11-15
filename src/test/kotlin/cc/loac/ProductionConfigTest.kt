package cc.loac

import cc.loac.plugins.validateProductionConfiguration
import io.ktor.server.config.ConfigLoader
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductionConfigTest {

    @Test
    fun `production config parses and keeps safe defaults`() {
        val config = ConfigLoader.load("config/application-prod.conf")

        assertEquals("false", config.property("ktor.development").getString())
        assertEquals("30000", config.property("ktor.deployment.shutdownGracePeriod").getString())
        assertEquals("30000", config.property("ktor.deployment.shutdownTimeout").getString())
        assertEquals("false", config.property("ktor.cors.anyHost").getString())
        assertEquals("nola", config.property("ktor.storage.databaseName").getString())
    }

    @Test
    fun `production validation rejects weak defaults`() {
        assertFailsWith<IllegalStateException> {
            testApplication {
                environment {
                    config = MapApplicationConfig(
                        "ktor.development" to "false",
                        "jwt.secret" to "jwt-secret",
                        "ktor.storage.password" to "123456",
                        "ktor.cors.anyHost" to "true"
                    )
                }
                application {
                    validateProductionConfiguration()
                }
            }
        }
    }
}
