package cc.loac.routes

import cc.loac.data.redis.RedisSingleton
import cc.loac.data.responses.HealthResponse
import cc.loac.data.sql.DatabaseSingleton
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.Date

/**
 * 健康检查路由
 * 仅暴露依赖是否可用，不返回连接串、异常栈等敏感信息
 */
fun Route.healthRouting() {
    get("/health") {
        val database = DatabaseSingleton.isHealthy()
        val redis = RedisSingleton.ping()
        val healthy = database && redis
        call.respond(
            if (healthy) HttpStatusCode.OK else HttpStatusCode.ServiceUnavailable,
            HealthResponse(
                status = if (healthy) "UP" else "DEGRADED",
                database = database,
                redis = redis,
                time = Date().time
            )
        )
    }
}
