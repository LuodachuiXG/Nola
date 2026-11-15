package cc.loac.routes

import cc.loac.services.AccessLogService
import cc.loac.utils.operate
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.receiveLongPathParam
import cc.loac.utils.receivePageAndSize
import cc.loac.utils.respondSuccess
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val accessLogService: AccessLogService by inject(AccessLogService::class.java)

/**
 * 访问日志管理员路由
 */
fun Route.accessLogAdminRouting() {
    route("/access-log") {
        authenticate {
            /** 分页获取访问日志，page=0 时获取全部 **/
            get {
                call.receivePageAndSize { page, size ->
                    call.respondSuccess(accessLogService.accessLogs(page, size))
                }
            }

            /** 获取访问日志总数 **/
            get("/count") {
                call.respondSuccess(accessLogService.count())
            }

            /** 获取单条访问日志 **/
            get("/{id}") {
                val id = call.receiveLongPathParam("id")
                call.respondSuccess(accessLogService.accessLog(id))
            }

            /** 批量删除访问日志 **/
            delete {
                val ids = call.receiveByDataClass<List<Long>>()
                if (ids.isEmpty()) {
                    call.respondSuccess(false)
                    return@delete
                }
                call.respondSuccess(accessLogService.deleteLogs(ids))
            }

            /** 删除全部访问日志 **/
            delete("/all") {
                call.respondSuccess(accessLogService.deleteAll().also {
                    if (it) operate(desc = "清空访问日志", call = call, isHighRisk = true)
                })
            }

            /** 删除指定时间以前的访问日志，时间戳单位为毫秒 **/
            delete("/before/{time}") {
                val time = call.receiveLongPathParam("time")
                call.respondSuccess(accessLogService.deleteBefore(time).also {
                    if (it) operate(desc = "清理 $time 以前的访问日志", call = call, isHighRisk = true)
                })
            }
        }
    }
}
