package cc.loac.routes

import cc.loac.data.exceptions.MyException
import cc.loac.data.requests.RestoreApplyRequest
import cc.loac.data.responses.RestorePreviewResponse
import cc.loac.extensions.toJSON
import cc.loac.services.BackupService
import cc.loac.services.PostService
import cc.loac.utils.RequestLimits
import cc.loac.utils.operate
import cc.loac.utils.receiveByDataClass
import cc.loac.utils.respondFailure
import cc.loac.utils.respondSuccess
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import io.ktor.utils.io.streams.*
import org.koin.java.KoinJavaComponent.inject
import java.io.File

private val postService: PostService by inject(PostService::class.java)
private val backupService: BackupService by inject(BackupService::class.java)

/**
 * 备份，路由
 */
fun Route.backupRouting() {
    /** 备份路由 **/
    authenticate {
        route("/backup") {
            /** 接收多个 Markdown 或 Txt 文件作为新文章 **/
            post("/post") {
                val multipartData = call.receiveMultipart()
                val fileNames = mutableListOf<String>()
                val fileContents = mutableListOf<String>()
                var fileCount = 0
                var totalBytes = 0L
                // 添加失败的结果集
                val errorResult = mutableListOf<String>()
                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            val fileName = part.originalFileName
                            if (!fileName.isNullOrBlank()) {
                                fileCount++
                                if (fileCount > RequestLimits.MAX_BACKUP_IMPORT_FILES) {
                                    throw PayloadTooLargeException(RequestLimits.MAX_BACKUP_IMPORT_FILES.toLong())
                                }
                                if (fileName.endsWith(".md") || fileName.endsWith(".txt")) {
                                    val fileBytes = part.provider().readRemaining(
                                        RequestLimits.MAX_BACKUP_IMPORT_FILE_BYTES + 1
                                    ).inputStream().readBytes()
                                    if (fileBytes.size > RequestLimits.MAX_BACKUP_IMPORT_FILE_BYTES) {
                                        throw PayloadTooLargeException(RequestLimits.MAX_BACKUP_IMPORT_FILE_BYTES)
                                    }
                                    totalBytes += fileBytes.size
                                    if (totalBytes > RequestLimits.MAX_BACKUP_IMPORT_TOTAL_BYTES) {
                                        throw PayloadTooLargeException(RequestLimits.MAX_BACKUP_IMPORT_TOTAL_BYTES)
                                    }
                                    fileNames.add(fileName)
                                    fileContents.add(fileBytes.toString(Charsets.UTF_8))
                                } else {
                                    // 不支持的文件类型
                                    errorResult.add("$fileName，文件类型错误。")
                                }
                            }
                        }

                        else -> {}
                    }
                }

                // 没有选择任何文件
                if (fileCount == 0) {
                    return@post call.respondFailure("请选择要导入的文件", HttpStatusCode.BadRequest)
                }

                // 添加成功的文章数组
                val successPosts = postService.addPost(fileNames, fileContents) { name, error ->
                    errorResult.add("$name，$error")
                }

                operate(desc = "导入文章备份，成功 ${successPosts.size} 篇", call = call)

                call.respondSuccess(
                    hashMapOf(
                        // 文件总数
                        "fileCount" to fileCount,
                        // 成功数量
                        "successCount" to successPosts.size,
                        // 失败数量
                        "failCount" to errorResult.size,
                        // 失败信息
                        "failResult" to errorResult
                    ).toJSON()
                )
            }

            /** 导出文章，将文章导出为 Markdown 格式的文件打成的压缩包 **/
            get("/post") {
                val result = backupService.exportPosts()
                operate(desc = "导出文章备份", call = call)
                call.respondSuccess(result)
            }

            /** 预览备份恢复，上传 zip 后返回令牌和预计操作，不修改数据 **/
            post("/restore/preview") {
                val multipartData = call.receiveMultipart()
                var preview: RestorePreviewResponse? = null
                multipartData.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val fileName = part.originalFileName
                        if (fileName.isNullOrBlank() || !fileName.endsWith(".zip", ignoreCase = true)) {
                            part.dispose()
                            throw MyException("请上传 .zip 格式的备份文件")
                        }
                        if (preview != null) {
                            part.dispose()
                            throw MyException("一次只能上传一个备份文件")
                        }
                        try {
                            preview = part.provider().toInputStream().use { input ->
                                backupService.stageRestore(input)
                            }
                        } finally {
                            part.dispose()
                        }
                    } else {
                        part.dispose()
                    }
                }
                val result = preview ?: return@post call.respondFailure(
                    "请选择要恢复的备份文件",
                    HttpStatusCode.BadRequest
                )
                call.respondSuccess(result)
            }

            /** 执行备份恢复，需要先调用预览接口获取令牌 **/
            post("/restore/apply") {
                val request = call.receiveByDataClass<RestoreApplyRequest> {
                    it.token.isNotBlank()
                }
                val result = backupService.applyRestore(request.token, request.conflictStrategy)
                operate(
                    desc = "恢复文章备份，新建文章 ${result.createdPosts} 篇，更新正文 ${result.updatedPosts} 篇，新增草稿 ${result.addedDrafts} 个",
                    call = call,
                    isHighRisk = true
                )
                call.respondSuccess(result)
            }

            /** 下载导出的文章备份压缩包 **/
            get("/download/{fileName}") {
                val fileName = call.parameters["fileName"]
                if (fileName.isNullOrBlank()) {
                    call.respondFailure("备份文件名不能为空", HttpStatusCode.BadRequest)
                    return@get
                }
                if (!fileName.endsWith(".zip", ignoreCase = true) ||
                    fileName.contains('/') ||
                    fileName.contains('\\')
                ) {
                    call.respondFailure("非法的备份文件名", HttpStatusCode.BadRequest)
                    return@get
                }

                val backupDir = File(".nola/backup").canonicalFile
                val backupFile = File(backupDir, fileName).canonicalFile
                if (backupFile.parentFile != backupDir || !backupFile.isFile) {
                    call.respondFailure("备份文件不存在", HttpStatusCode.NotFound)
                    return@get
                }

                call.response.header(
                    HttpHeaders.ContentDisposition,
                    ContentDisposition.Attachment.withParameter(
                        ContentDisposition.Parameters.FileName,
                        fileName
                    ).toString()
                )
                call.respondFile(backupFile)
            }
        }
    }
}
