package cc.loac.routes

import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.utils.receivePageAndSize
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PaginationLimitsTest {

    @Test
    fun `size above limit is rejected`() = testApplication {
        application { paginationTestApp() }

        assertEquals(HttpStatusCode.Conflict, client.get("/page/1/101").status)
    }

    @Test
    fun `page zero without principal is rejected`() = testApplication {
        application { paginationTestApp() }

        assertEquals(HttpStatusCode.Conflict, client.get("/page/0/0").status)
    }

    @Test
    fun `non-positive page or size is rejected`() = testApplication {
        application { paginationTestApp() }

        assertEquals(HttpStatusCode.Conflict, client.get("/page/1/0").status)
        assertEquals(HttpStatusCode.Conflict, client.get("/page/-1/10").status)
    }

    @Test
    fun `valid page and size are accepted`() = testApplication {
        application { paginationTestApp() }

        val response = client.get("/page/1/100")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("1:100", response.bodyAsText())
    }

    private fun Application.paginationTestApp() {
        install(StatusPages) {
            exception<ParamMismatchException> { call, _ ->
                call.respond(HttpStatusCode.Conflict)
            }
        }
        routing {
            get("/page/{page}/{size}") {
                call.receivePageAndSize { page, size ->
                    call.respondText("$page:$size")
                }
            }
        }
    }
}
