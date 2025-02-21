package cc.loac.routes

import cc.loac.data.models.enums.CommentSort
import cc.loac.extensions.isEnum
import cc.loac.services.OperationService
import cc.loac.utils.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val operationService: OperationService by inject(OperationService::class.java)

/**
 * 操作记录管理员路由
 */
fun Route.operationAdminRouting() {
    route("/operation") {
        authenticate {
            /** 删除操作记录 **/
            delete {
                // 操作记录 ID 数组
                val ids = call.receiveByDataClass<List<Long>>()
                // 操作记录 ID 列表为空
                if (ids.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(operationService.deleteOperation(ids))
            }

            /** 删除全部操作记录 **/
            delete("/all") {
                call.respondSuccess(operationService.deleteAllOperations())
            }

            /** 删除指定时间以前的所有数据 **/
            delete("/before/{time}") {
                // 时间戳（毫秒）
                val time = call.receiveLongPathParam("time")
                call.respondSuccess(operationService.deleteBefore(time))
            }

            /** 分页获取操作记录 **/
            get {
                call.receivePageAndSize { page, size ->
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<CommentSort>()
                    }?.let { CommentSort.valueOf(it) }

                    call.respondSuccess(operationService.operations(page, size, sort))
                }
            }

        }
    }
}