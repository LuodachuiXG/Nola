package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.services.FileService
import cc.loac.utils.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val fileService: FileService by inject(FileService::class.java)

/**
 * 文件，管理员路由
 */
fun Route.fileAdminRouting() {
    route("/file") {
        authenticate {
            // 文件存储方式相关路由
            fileStorageModeRouting()

            // 文件组相关路由
            fileGroupRouting()
        }
    }
}

/**
 * 文件组相关路由
 */
private fun Route.fileGroupRouting() {
    /** 添加文件组 **/
    post("/group") {
        val fileGroup = call.receiveByDataClass<FileGroup>()
        call.respondSuccess(fileService.addFileGroup(fileGroup) ?: throw AddFailedException())
    }

    /** 删除文件组 **/
    delete("/group/{fileGroupId}") {
        val fileGroupId = call.receiveIntPathParam("fileGroupId")
        call.respondSuccess(fileService.deleteFileGroup(fileGroupId))
    }

    /** 修改文件组 **/
    put("/group") {
        val fileGroup = call.receiveByDataClass<FileGroupUpdateRequest> {
            it.fileGroupId >= 0
        }
        call.respondSuccess(fileService.updateFileGroup(fileGroup))
    }

    /** 根据文件组存储方式获取文件组 **/
    get("/group/{fileStorageMode}") {
        val fileStorageMode = call.receiveNullablePathParam("fileStorageMode") {
            it?.isEnum<FileStorageModeEnum>()
        }?.let { FileStorageModeEnum.valueOf(it) }
        call.respondSuccess(fileService.getFileGroups(fileStorageMode))
    }
}

/**
 * 文件存储方式相关路由
 */
private fun Route.fileStorageModeRouting() {
    /** 获取已经设置的所有存储方式 **/
    get("/modes") {
        call.respondSuccess(fileService.getModes())
    }

    /** 设置腾讯云对象存储 */
    post("/tencent_cos") {
        val config = call.receiveByDataClass<TencentCOSConfig>()
        call.respondSuccess(fileService.setTencentCOS(config))
    }

    /** 删除腾讯云对象存储配置 **/
    delete("/tencent_cos") {
        call.respondSuccess(fileService.deleteTencentCOS())
    }

    /** 获取腾讯云对象存储配置 **/
    get("/tencent_cos") {
        call.respondSuccess(fileService.getTencentCOS())
    }
}