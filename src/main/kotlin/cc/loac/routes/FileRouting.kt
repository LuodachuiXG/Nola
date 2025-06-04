package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.exceptions.ParamMismatchException
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.enums.FileSort
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.requests.FileMoveRequest
import cc.loac.data.requests.FileRecordRequest
import cc.loac.extensions.isEnum
import cc.loac.extensions.isInt
import cc.loac.services.FileService
import cc.loac.utils.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import io.ktor.utils.io.streams.*
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream

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
        var fileGroupId: Long? = null
        // 文件存储方式
        var storageMode: FileStorageModeEnum? = null
        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    if (part.name == "fileGroupId" && part.value.isNotBlank()) {
                        if (part.value.isInt()) fileGroupId = part.value.toLong()
                    }

                    if (part.name == "storageMode" && part.value.isNotBlank()) {
                        if (!part.value.isEnum<FileStorageModeEnum>()) throw ParamMismatchException()
                        storageMode = FileStorageModeEnum.valueOf(part.value)
                    }
                }

                is PartData.FileItem -> {
                    inputStream = part.provider().readRemaining().inputStream()
                    originName = part.originalFileName
                }

                else -> {}
            }
            part.dispose()
        }
        // 如果文件流为空，则不执行任何操作
        inputStream ?: return@post call.respondSuccess(false)
        if (originName.isNullOrBlank()) originName = "未命名文件"
        // 上传的文件长度
        val fileLength = call.request.header("Content-Length")?.toLong()
        call.respondSuccess(
            // 上传文件
            fileService.uploadFile(
                inputStream = inputStream,
                fileName = originName!!,
                storageMode = storageMode ?: FileStorageModeEnum.LOCAL,
                fileGroupId = fileGroupId,
                fileLength = fileLength
            )?.also {
                operate(
                    desc = "上传文件：[$originName]",
                    call = call
                )
            }
        )
    }


    /** 添加文件记录 **/
    post("/record") {
        val request = call.receiveByDataClass<FileRecordRequest>()
        call.respondSuccess(fileService.uploadFileRecord(request)?.also {
            operate(
                desc = "添加文件记录：[${it.displayName}]",
                call = call
            )
        })
    }

    /** 根据文件 ID 集合删除文件 **/
    delete {
        val ids = call.receiveByDataClass<List<Long>>()
        call.respondSuccess(fileService.deleteFiles(ids).also {
            if (it.isNotEmpty()) {
                operate(
                    desc = "删除文件：[${ids.joinToString(", ")}]",
                    call = call
                )
            }
        })
    }

    /** 根据文件索引数据类集合删除文件 **/
    delete("/name") {
        val fileIndexes = call.receiveByDataClass<List<FileIndex>>()
        call.respondSuccess(fileService.deleteFilesByFineIndexes(fileIndexes).also {
            if (it.isNotEmpty()) {
                operate(
                    desc = "删除文件：[${fileIndexes.joinToString(", ") { it -> it.name }}]",
                    call = call
                )
            }
        })
    }

    /** 移动文件 **/
    put {
        val fileMoveRequest = call.receiveByDataClass<FileMoveRequest>()
        call.respondSuccess(fileService.moveFiles(fileMoveRequest).also {
            if (it.isNotEmpty()) {
                operate(
                    desc = "移动文件到新分组，文件 ID: [${fileMoveRequest.fileIds.joinToString(", ")}]，新分组 ID: [${fileMoveRequest.newFileGroupId}]",
                    call = call
                )
            }
        })
    }

    /** 获取文件 **/
    get {
        call.receivePageAndSize { page, size ->
            val sort = call.receiveNullablePathParam("sort") {
                it?.isEnum<FileSort>()
            }?.let { FileSort.valueOf(it) }
            val mode = call.receiveNullablePathParam("mode") {
                it?.isEnum<FileStorageModeEnum>()
            }?.let { FileStorageModeEnum.valueOf(it) }
            val groupId = call.receiveNullablePathParam("groupId") {
                it?.isInt()
            }?.toLong()
            val key = call.receiveNullablePathParam("key")

            call.respondSuccess(fileService.getFiles(page, size, sort, mode, groupId, key))
        }
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
            call.respondSuccess(fileService.addFileGroup(fileGroup)?.also {
                operate(
                    desc = "添加文件组：[${fileGroup.displayName}]",
                    call = call
                )
            } ?: throw AddFailedException())
        }

        /** 删除文件组 **/
        delete("/{fileGroupId}") {
            val fileGroupId = call.receiveIntPathParam("fileGroupId").toLong()
            call.respondSuccess(fileService.deleteFileGroup(fileGroupId).also {
                if (it) {
                    operate(
                        desc = "删除文件组：[${fileGroupId}]",
                        call = call
                    )
                }
            })
        }

        /** 修改文件组 **/
        put {
            val fileGroup = call.receiveByDataClass<FileGroupUpdateRequest> {
                it.fileGroupId >= 0
            }
            call.respondSuccess(fileService.updateFileGroup(fileGroup).also {
                if (it) {
                    operate(
                        desc = "修改文件组，ID: [${fileGroup.fileGroupId}]，新名称：[${fileGroup.displayName}]",
                        call = call
                    )
                }
            })
        }

        /** 根据文件组存储方式获取文件组 **/
        get {
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
    route("/mode") {
        /** 获取已经设置的所有存储方式 **/
        get {
            call.respondSuccess(fileService.getModes())
        }

        /** 设置腾讯云对象存储 */
        post("/tencent_cos") {
            val config = call.receiveByDataClass<TencentCOSConfig>()
            call.respondSuccess(fileService.setTencentCOS(config).also {
                if (it) {
                    operate(
                        desc = "设置腾讯云对象存储配置",
                        call = call
                    )
                }
            })
        }

        /** 删除腾讯云对象存储配置 **/
        delete("/tencent_cos") {
            call.respondSuccess(fileService.deleteTencentCOS().also {
                if (it) {
                    operate(
                        desc = "删除腾讯云对象存储配置",
                        call = call
                    )
                }
            })
        }

        /** 获取腾讯云对象存储配置 **/
        get("/tencent_cos") {
            call.respondSuccess(fileService.getTencentCOS())
        }
    }
}