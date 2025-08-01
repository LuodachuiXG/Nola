package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.models.enums.DiarySort
import cc.loac.data.requests.DiaryRequest
import cc.loac.extensions.isEnum
import cc.loac.services.DiaryService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject

private val diaryService: DiaryService by inject(DiaryService::class.java)

/**
 * 日记，管理员路由
 */
fun Route.diaryAdminRouting() {
    route("diary") {
        authenticate {
            /** 添加日记 **/
            post {
                val diary = call.receiveByDataClass<DiaryRequest>()
                call.respondSuccess(diaryService.addDiary(diary)?.also {
                    operate(
                        // 只取前 20 个字符
                        desc = "添加日记 [${diary.content.take(20)}]}",
                        call = call
                    )
                } ?: throw AddFailedException())
            }

            /** 删除日记 **/
            delete {
                val ids = call.receiveByDataClass<List<Long>>()
                if (ids.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(diaryService.deleteDiaries(ids).also {
                    if (it) {
                        operate(
                            desc = "删除日记 [${ids.joinToString(", ")}]",
                            call = call
                        )
                    }
                })
            }

            /** 修改日记 **/
            put {
                val diary = call.receiveByDataClass<DiaryRequest> {
                    it.diaryId != null
                }
                call.respondSuccess(diaryService.updateDiary(diary).also {
                    if (it) {
                        operate(
                            desc = "修改日记 [${diary.diaryId}]",
                            call = call
                        )
                    }
                })
            }

            /** 获取日志 **/
            get {
                call.receivePageAndSize { page, size ->
                    // 可空的日志排序
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<DiarySort>()
                    }?.let { DiarySort.valueOf(it) }
                    call.respondSuccess(diaryService.diaries(page, size, sort))
                }
            }
        }
    }
}

/**
 * 友情链接，API 路由
 */
fun Route.diaryApiRouting() {
    route("diary") {
        /** 获取日记 **/
        get {
            call.receivePageAndSize { page, size ->
                val diariesPager = diaryService.diaries(page, size)
                call.respondSuccess(diariesPager)
            }
        }
    }
}