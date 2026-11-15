package cc.loac

import cc.loac.plugins.configureSecurityHeaders
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ApplicationTest {

    @Test
    fun `security headers are added to responses`() = testApplication {
        application {
            configureSecurityHeaders()
            routing {
                get("/ping") {
                    call.respondText("pong")
                }
            }
        }

        val response = client.get("/ping")

        assertEquals("nosniff", response.headers["X-Content-Type-Options"])
        assertEquals("SAMEORIGIN", response.headers["X-Frame-Options"])
        assertEquals("no-referrer", response.headers["Referrer-Policy"])
        // 非 HTTPS 直连不应返回 HSTS
        assertNull(response.headers["Strict-Transport-Security"])
    }
}
