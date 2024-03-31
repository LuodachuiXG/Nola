package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.requests.FileMoveRequest
import cc.loac.services.FileService
import cc.loac.utils.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream
import kotlin.math.log

private val fileService: FileService by inject(FileService::class.java)

/**
 * 文件，管理员路由
 */
fun Route.fileAdminRouting() {
    route("/file") {
        authenticate {
            // 文件存储方式相关路由
            fileStorageModeRouting()

            // 文件相关路由
            fileRouting()

            // 文件组相关路由
            fileGroupRouting()
        }
    }
}

/**
 * 文件相关路由
 */
private fun Route.fileRouting() {
    /** 添加文件 **/
    post {
        // 接收传过来的文件流
        val multipartData = call.receiveMultipart()
        // 文件输入流
        var inputStream: InputStream? = null
        // 文件原始文件名
        var originName: String? = null
        // 文件组 ID
        var fileGroupId: Int? = null
        // 文件存储方式
        var storageMode: FileStorageModeEnum? = null
        multipartData.forEachPart { part ->
            when(part) {
                is PartData.FormItem -> {
                    if (part.name == "fileGroupId") {
                        if (part.value.isInt()) fileGroupId = part.value.toInt()
                    }

                    if (part.name == "storageMode") {
                        if (!part.value.isEnum<FileStorageModeEnum>()) throw ParamMismatchException()
                        storageMode = FileStorageModeEnum.valueOf(part.value)
                    }
                }
                is PartData.FileItem -> {
                    inputStream = part.streamProvider()
                    originName = part.originalFileName
                }
                else -> {}
            }
        }
        // 如果文件流为空，则不执行任何操作
        inputStream ?: return@post call.respondSuccess(false)
        if (originName.isNullOrBlank()) originName = "未命名文件"
        // 上传的文件长度
        val fileLength = call.request.header("Content-Length")?.toLong()
        call.respondSuccess(
            // 上传文件
            fileService.uploadFile(
                inputStream = inputStream!!,
                fileName = originName!!,
                storageMode = storageMode ?: FileStorageModeEnum.LOCAL,
                fileGroupId = fileGroupId,
                fileLength = fileLength
            )
        )
    }

    /** 根据文件 ID 集合删除文件 **/
    delete {
        val ids = call.receiveByDataClass<List<Int>>()
        call.respondSuccess(fileService.deleteFiles(ids))
    }

    /** 根据文件索引数据类集合删除文件 **/
    delete("/name") {
        val fileIndexes = call.receiveByDataClass<List<FileIndex>>()
        call.respondSuccess(fileService.deleteFilesByFineIndexes(fileIndexes))
    }

    /** 移动文件 **/
    put {
        val fileMoveRequest = call.receiveByDataClass<FileMoveRequest>()
        fileMoveRequest.toString().error()
        call.respondSuccess(fileService.moveFiles(fileMoveRequest))
    }
}

/**
 * 文件组相关路由
 */
private fun Route.fileGroupRouting() {
    route("/group") {
        /** 添加文件组 **/
        post {
            val fileGroup = call.receiveByDataClass<FileGroup>()
            call.respondSuccess(fileService.addFileGroup(fileGroup) ?: throw AddFailedException())
        }

        /** 删除文件组 **/
        delete("/{fileGroupId}") {
            val fileGroupId = call.receiveIntPathParam("fileGroupId")
            call.respondSuccess(fileService.deleteFileGroup(fileGroupId))
        }

        /** 修改文件组 **/
        put {
            val fileGroup = call.receiveByDataClass<FileGroupUpdateRequest> {
                it.fileGroupId >= 0
            }
            call.respondSuccess(fileService.updateFileGroup(fileGroup))
        }

        /** 根据文件组存储方式获取文件组 **/
        get("/{fileStorageMode}") {
            val fileStorageMode = call.receiveNullablePathParam("fileStorageMode") {
                it?.isEnum<FileStorageModeEnum>()
            }?.let { FileStorageModeEnum.valueOf(it) }
            call.respondSuccess(fileService.getFileGroups(fileStorageMode))
        }
    }
}

/**
 * 文件存储方式相关路由
 */
private fun Route.fileStorageModeRouting() {
    /** 获取已经设置的所有存储方式 **/
    route("/mode") {
        get("/list") {
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
}