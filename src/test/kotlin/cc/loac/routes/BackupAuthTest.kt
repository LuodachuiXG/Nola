package cc.loac.routes

import cc.loac.TestKoin
import cc.loac.FakeTokenService
import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.plugins.configureSecurity
import cc.loac.plugins.configureSerialization
import cc.loac.plugins.configureStatusPage
import cc.loac.security.token.TokenConfig
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackupAuthTest {

    private val issuer = "http://localhost:8098"
    private val audience = "users"
    private val secret = "test-secret"
    private val tokenConfig = TokenConfig(issuer, audience, 60_000, secret)

    @BeforeTest
    fun setup() {
        TestKoin.start()
        FakeTokenService.validToken = null
    }

    @Test
    fun `backup download without token returns unauthorized`() = testApplication {
        environment { config = MapApplicationConfig("jwt.realm" to "Nola") }
        application {
            configureTestApp()
        }

        assertEquals(
            HttpStatusCode.Unauthorized,
            client.get("/admin/backup/download/missing.zip").status
        )
    }

    @Test
    fun `backup download with invalid token returns unauthorized`() = testApplication {
        environment { config = MapApplicationConfig("jwt.realm" to "Nola") }
        application {
            configureTestApp()
        }

        assertEquals(
            HttpStatusCode.Unauthorized,
            client.get("/admin/backup/download/missing.zip") {
                header(HttpHeaders.Authorization, "Bearer invalid-token")
            }.status
        )
    }

    @Test
    fun `backup download with valid session token passes authentication`() = testApplication {
        environment { config = MapApplicationConfig("jwt.realm" to "Nola") }
        val token = createToken()
        FakeTokenService.validToken = token
        application {
            configureTestApp()
        }

        val response = client.get("/admin/backup/download/missing.zip") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        // 通过鉴权后进入路由内部，文件不存在返回 404
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `backup download rejects non-zip and path separator file names`() = testApplication {
        environment { config = MapApplicationConfig("jwt.realm" to "Nola") }
        val token = createToken()
        FakeTokenService.validToken = token
        application {
            configureTestApp()
        }

        val invalidType = client.get("/admin/backup/download/evil.txt") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }
        val pathSeparator = client.get("/admin/backup/download/evil%5Csecret.zip") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }

        assertEquals(HttpStatusCode.BadRequest, invalidType.status)
        assertEquals(HttpStatusCode.BadRequest, pathSeparator.status)
    }

    private fun createToken(): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withExpiresAt(Date(System.currentTimeMillis() + 60_000))
        .withClaim(TokenClaimEnum.USER_ID.key, 1L)
        .withClaim(TokenClaimEnum.USERNAME.key, "admin")
        .sign(Algorithm.HMAC256(secret))

    private fun io.ktor.server.application.Application.configureTestApp() {
        configureSerialization()
        configureSecurity(tokenConfig)
        configureStatusPage()
        routing {
            route("/admin") {
                backupRouting()
            }
        }
    }
}
