package cc.loac.routes

import cc.loac.services.PostService
import cc.loac.utils.error
import cc.loac.utils.markdownToHtml
import cc.loac.utils.respondSuccess
import cc.loac.utils.toJSON
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val postService: PostService by inject(PostService::class.java)

/**
 * 备份，路由
 */
fun Route.backupRouting() {
    /** 备份路由 **/
    route("/backup") {
        /** 接收多个 Markdown 文件 **/
        post("/md") {
            val multipartData = call.receiveMultipart()
            val fileNames = mutableListOf<String>()
            val fileContents = mutableListOf<String>()
            var fileCount = 0
            // 添加失败的结果集
            val errorResult = mutableListOf<String>()
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        val fileName = part.originalFileName
                        if (!fileName.isNullOrBlank()) {
                            fileCount++
                            if (fileName.endsWith(".md") || fileName.endsWith(".txt")) {
                                fileNames.add(fileName)
                                fileContents.add(part.streamProvider().bufferedReader().readText())
                            } else {
                                // 不支持的文件类型
                                errorResult.add("$fileName，文件类型错误。")
                            }
                        }
                    }

                    else -> {}
                }
            }

            // 添加成功的文章数组
            val successPosts = postService.addPost(fileNames, fileContents) { name, error ->
                errorResult.add("$name，$error")
            }

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
    }
}