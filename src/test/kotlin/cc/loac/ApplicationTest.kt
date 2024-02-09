package cc.loac

import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.sql.dao.impl.configDao
import cc.loac.plugins.*
import cc.loac.routes.configRouting
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {

    }
}
