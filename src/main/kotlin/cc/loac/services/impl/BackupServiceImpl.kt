package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Post
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.ExportPostResponse
import cc.loac.data.responses.RestorePreviewFile
import cc.loac.data.responses.RestorePreviewResponse
import cc.loac.data.responses.RestoreResultResponse
import cc.loac.services.BackupService
import cc.loac.services.PostService
import cc.loac.utils.RequestLimits
import cc.loac.utils.error
import cc.loac.utils.info
import cc.loac.utils.randomString
import cc.loac.utils.secureRandomString
import io.ktor.server.plugins.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import java.util.zip.ZipException
import java.util.zip.ZipInputStream

/**
 * 备份服务实现类
 * 负责备份保留策略、定时备份以及基于备份包的预览式恢复
 */
class BackupServiceImpl : BackupService {

    private val postService: PostService by inject(PostService::class.java)
    private val ioScope: CoroutineScope by inject(CoroutineScope::class.java)

    private val schedulerStarted = AtomicBoolean(false)
    private val pendingRestores = ConcurrentHashMap<String, PendingRestore>()

    override suspend fun exportPosts(): ExportPostResponse {
        val result = postService.exportPosts()
        runCatching { cleanupOldBackups() }
            .onFailure { "清理过期备份失败：${it.message}".error() }
        return result
    }

    override suspend fun stageRestore(inputStream: InputStream): RestorePreviewResponse = withContext(Dispatchers.IO) {
        cleanupPendingRestores()

        val token = secureRandomString(32)
        val tempDir = File(BACKUP_TEMP_DIR).canonicalFile
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            throw MyException("无法创建备份恢复临时目录")
        }
        val zipFile = File(tempDir, "restore_$token.zip").canonicalFile
        if (zipFile.parentFile != tempDir) throw MyException("恢复临时文件路径不合法")

        try {
            FileOutputStream(zipFile).use { output ->
                copyLimited(inputStream, output)
            }
            val parsed = parseBackupZip(zipFile)
            val preview = buildPreview(token, parsed)
            pendingRestores[token] = PendingRestore(
                zipFile = zipFile,
                expiresAt = preview.expiresAt
            )
            trimPendingRestores(token)
            preview
        } catch (e: Exception) {
            zipFile.delete()
            throw e
        }
    }

    override suspend fun applyRestore(token: String, conflictStrategy: String?): RestoreResultResponse =
        withContext(Dispatchers.IO) {
            val pending = pendingRestores.remove(token)
                ?: throw MyException("恢复令牌不存在或已失效，请重新预览")
            try {
                if (pending.expiresAt < System.currentTimeMillis()) {
                    throw MyException("恢复令牌已过期，请重新预览")
                }
                val strategy = parseConflictStrategy(conflictStrategy)
                val parsed = parseBackupZip(pending.zipFile)
                applyEntries(parsed.entries, strategy)
            } finally {
                pending.zipFile.delete()
            }
        }

    override fun startScheduler() {
        if (!schedulerStarted.compareAndSet(false, true)) return
        val intervalHours = envLong("NOLA_BACKUP_INTERVAL_HOURS", DEFAULT_BACKUP_INTERVAL_HOURS)
        ioScope.launch {
            runCatching { cleanupOldBackups() }
                .onFailure { "启动时清理过期备份失败：${it.message}".error() }
            if (intervalHours <= 0) {
                "定时备份已禁用（NOLA_BACKUP_INTERVAL_HOURS=0）".info()
                return@launch
            }
            "定时备份已启用，间隔 ${intervalHours} 小时".info()
            while (isActive) {
                delay(intervalHours * 60 * 60 * 1000)
                runCatching { scheduledBackup() }
                    .onFailure { "定时备份失败：${it.message}".error() }
            }
        }
    }

    private suspend fun scheduledBackup() {
        if (postService.postCount() == 0L) {
            "定时备份跳过：当前没有文章".info()
            return
        }
        val result = postService.exportPosts()
        cleanupOldBackups()
        "定时备份完成：成功 ${result.successCount}，失败 ${result.failCount}，文件：${result.path}".info()
    }

    /**
     * 解析压缩包内容，所有读取均限制大小，避免 zip bomb
     */
    private fun parseBackupZip(zipFile: File): ParsedBackup {
        val entries = mutableListOf<BackupFileEntry>()
        val invalidFiles = mutableListOf<RestorePreviewFile>()
        var entryCount = 0
        var totalBytes = 0L

        try {
            ZipInputStream(zipFile.inputStream().buffered()).use { zip ->
                var zipEntry = zip.nextEntry
                while (zipEntry != null) {
                    entryCount++
                    if (entryCount > RequestLimits.MAX_RESTORE_ENTRIES) {
                        throw MyException("备份包文件数量超过限制（${RequestLimits.MAX_RESTORE_ENTRIES}）")
                    }
                    if (!zipEntry.isDirectory) {
                        val name = zipEntry.name
                        if (!isSafeBackupName(name)) {
                            invalidFiles += invalidPreview(name, "文件名不合法，只支持压缩包根目录下的 .md 文件")
                        } else if (!name.endsWith(".md", ignoreCase = true)) {
                            invalidFiles += invalidPreview(name, "不是 .md 文件")
                        } else {
                            val contentBytes = readEntryLimited(zip)
                            totalBytes += contentBytes.size
                            if (totalBytes > RequestLimits.MAX_RESTORE_TOTAL_BYTES) {
                                throw PayloadTooLargeException(RequestLimits.MAX_RESTORE_TOTAL_BYTES)
                            }
                            val parsed = parseBackupFileName(name)
                            if (parsed == null) {
                                invalidFiles += invalidPreview(name, "文件名不是 Nola 导出的文章备份格式")
                            } else {
                                entries += parsed.copy(content = contentBytes.toString(Charsets.UTF_8))
                            }
                        }
                    }
                    zip.closeEntry()
                    zipEntry = zip.nextEntry
                }
            }
        } catch (e: ZipException) {
            throw MyException("无法解析备份压缩包：${e.message}")
        }

        if (entryCount == 0) throw MyException("备份压缩包为空")
        if (entries.isEmpty()) {
            throw MyException("备份压缩包内没有可恢复的 Markdown 文件")
        }
        return ParsedBackup(entries, invalidFiles)
    }

    private suspend fun buildPreview(token: String, parsed: ParsedBackup): RestorePreviewResponse {
        val files = mutableListOf<RestorePreviewFile>()
        var createPostCount = 0
        var updatePostCount = 0
        var addDraftCount = 0
        var conflictCount = 0
        var skipCount = 0

        files += parsed.invalidFiles
        val groups = parsed.entries.groupBy { it.postId }
        groups.forEach { (postId, entries) ->
            val slug = entries.first().slug
            val existingById = findPostById(postId)
            val target = existingById?.takeIf { it.slug == slug }
            val conflict = existingById != null && existingById.slug != slug

            if (conflict) {
                conflictCount += entries.size
                entries.forEach {
                    files += it.toPreview(
                        action = "CONFLICT",
                        reason = "文章 ID 已存在，但别名不一致（当前：${existingById!!.slug}）"
                    )
                }
                return@forEach
            }

            if (target == null) {
                val slugTaken = postService.postBySlug(slug) != null
                if (slugTaken) {
                    conflictCount += entries.size
                    entries.forEach {
                        files += it.toPreview(
                            action = "CONFLICT",
                            reason = "别名 [$slug] 已被其他文章占用"
                        )
                    }
                    return@forEach
                }
            }

            val published = entries.lastOrNull { !it.isDraft }
            if (published != null) {
                if (target == null) {
                    createPostCount++
                    files += published.toPreview(
                        action = "CREATE",
                        reason = "将新建文章（仅恢复正文，不包含标签、分类、加密密码等元数据）"
                    )
                } else {
                    updatePostCount++
                    files += published.toPreview(
                        action = "UPDATE_CONTENT",
                        reason = if (target.status == PostStatus.DELETED) {
                            "将更新正文，但文章仍在回收站，不会自动恢复为已发布"
                        } else {
                            "将覆盖现有正文"
                        }
                    )
                }
            }

            entries.filter { it.isDraft }.forEach { draft ->
                if (target == null && published == null) {
                    skipCount++
                    files += draft.toPreview(
                        action = "SKIP",
                        reason = "没有对应的正文文件，草稿不会单独创建文章"
                    )
                } else if (target == null) {
                    addDraftCount++
                    files += draft.toPreview(
                        action = "ADD_DRAFT",
                        reason = "将在新建文章下创建草稿"
                    )
                } else if (postService.postContent(target.postId, PostContentStatus.DRAFT, draft.draftName) != null) {
                    skipCount++
                    files += draft.toPreview(
                        action = "SKIP",
                        reason = "同名草稿已存在，不会覆盖"
                    )
                } else {
                    addDraftCount++
                    files += draft.toPreview(
                        action = "ADD_DRAFT",
                        reason = "将新增草稿"
                    )
                }
            }
        }

        val warnings = mutableListOf(
            "恢复只还原文章正文和草稿，不包含文章标题、摘要、标签、分类、加密密码、评论等元数据。",
            "新建文章将使用备份中的别名作为标题，状态为已发布且可见。"
        )
        if (conflictCount > 0) {
            warnings += "存在别名或文章 ID 冲突，默认跳过；可在执行时使用 RENAME 策略为冲突文章生成新别名。"
        }

        return RestorePreviewResponse(
            token = token,
            expiresAt = System.currentTimeMillis() + RESTORE_TOKEN_TTL_MILLIS,
            totalFiles = parsed.entries.size + parsed.invalidFiles.size,
            publishedFiles = parsed.entries.count { !it.isDraft },
            draftFiles = parsed.entries.count { it.isDraft },
            createPostCount = createPostCount,
            updatePostCount = updatePostCount,
            addDraftCount = addDraftCount,
            conflictCount = conflictCount,
            skipCount = skipCount,
            invalidCount = parsed.invalidFiles.size,
            files = files,
            warnings = warnings
        )
    }

    private suspend fun applyEntries(
        entries: List<BackupFileEntry>,
        strategy: RestoreConflictStrategy
    ): RestoreResultResponse {
        var createdPosts = 0
        var updatedPosts = 0
        var addedDrafts = 0
        var skippedFiles = 0
        var failedFiles = 0
        val failures = mutableListOf<String>()

        entries.groupBy { it.postId }.forEach { (postId, groupEntries) ->
            val slug = groupEntries.first().slug
            val existingById = findPostById(postId)
            val target = existingById?.takeIf { it.slug == slug }
            val hasIdConflict = existingById != null && target == null
            val slugOwner = postService.postBySlug(slug)
            val slugTakenByOther = slugOwner != null && slugOwner.postId != postId
            val published = groupEntries.lastOrNull { !it.isDraft }
            val drafts = groupEntries.filter { it.isDraft }

            try {
                if (hasIdConflict && strategy == RestoreConflictStrategy.SKIP) {
                    skippedFiles += groupEntries.size
                    failures += "文章 ID [$postId] 已存在但别名不一致，已跳过"
                    return@forEach
                }
                if (slugTakenByOther && strategy == RestoreConflictStrategy.SKIP) {
                    skippedFiles += groupEntries.size
                    failures += "别名 [$slug] 已被文章 [${slugOwner!!.postId}] 占用，已跳过"
                    return@forEach
                }

                if (target == null) {
                    val newPost = published?.let {
                        val newSlug = if (slugTakenByOther || hasIdConflict) {
                            uniqueSlug(slug)
                        } else {
                            slug
                        }
                        postService.addPost(
                            PostRequest(
                                postId = null,
                                title = slug,
                                autoGenerateExcerpt = true,
                                excerpt = null,
                                slug = newSlug,
                                allowComment = true,
                                status = PostStatus.PUBLISHED,
                                visible = PostVisible.VISIBLE,
                                content = it.content,
                                categoryId = null,
                                tagIds = emptyList(),
                                cover = null,
                                pinned = false,
                                encrypted = false,
                                password = null
                            )
                        )
                    }
                    if (published != null) {
                        if (newPost == null) {
                            failedFiles += groupEntries.size
                            failures += "文章 [$slug] 新建失败"
                            return@forEach
                        }
                        createdPosts++
                    }
                    if (newPost == null && drafts.isNotEmpty()) {
                        skippedFiles += drafts.size
                        failures += "文章 [$slug] 没有正文文件，草稿未恢复"
                        return@forEach
                    }
                    newPost?.let { post ->
                        drafts.forEach { draft ->
                            val result = postService.addPostDraft(post.postId, draft.content, draft.draftName!!)
                            if (result != null) {
                                addedDrafts++
                            } else {
                                failedFiles++
                                failures += "[${draft.fileName}] 草稿恢复失败"
                            }
                        }
                    }
                } else {
                    if (published != null) {
                        val updated = postService.updatePostContent(
                            PostContentRequest(target.postId, published.content),
                            PostContentStatus.PUBLISHED
                        )
                        if (updated) {
                            updatedPosts++
                        } else {
                            failedFiles++
                            failures += "[${published.fileName}] 正文更新失败（文章可能缺少正文记录）"
                        }
                    }
                    drafts.forEach { draft ->
                        if (postService.postContent(target.postId, PostContentStatus.DRAFT, draft.draftName) != null) {
                            skippedFiles++
                        } else {
                            val result = postService.addPostDraft(target.postId, draft.content, draft.draftName!!)
                            if (result != null) {
                                addedDrafts++
                            } else {
                                failedFiles++
                                failures += "[${draft.fileName}] 草稿恢复失败"
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                failedFiles += groupEntries.size
                failures += "文章 [$slug] 恢复失败：${e.message}"
            }
        }

        return RestoreResultResponse(
            createdPosts = createdPosts,
            updatedPosts = updatedPosts,
            addedDrafts = addedDrafts,
            skippedFiles = skippedFiles,
            failedFiles = failedFiles,
            failures = failures
        )
    }

    private suspend fun findPostById(postId: Long): Post? {
        return postService.posts(listOf(postId), false).firstOrNull()
    }

    private suspend fun uniqueSlug(base: String): String {
        var slug = "${base}_${randomString(6)}"
        while (postService.postBySlug(slug) != null) {
            slug = "${base}_${randomString(6)}"
        }
        return slug
    }

    private fun parseConflictStrategy(value: String?): RestoreConflictStrategy {
        return when (value?.trim()?.uppercase()) {
            null, "", "SKIP" -> RestoreConflictStrategy.SKIP
            "RENAME" -> RestoreConflictStrategy.RENAME
            else -> throw MyException("不支持的冲突处理策略：$value")
        }
    }

    /**
     * 清理过期备份和过期恢复临时文件
     */
    private fun cleanupOldBackups() {
        val retentionDays = envLong("NOLA_BACKUP_RETENTION_DAYS", DEFAULT_RETENTION_DAYS)
        val retentionCount = envLong("NOLA_BACKUP_RETENTION_COUNT", DEFAULT_RETENTION_COUNT)

        val backupDir = File(BACKUP_DIR).canonicalFile
        if (backupDir.isDirectory) {
            val backups = backupDir.listFiles { file ->
                file.isFile && file.extension.equals("zip", ignoreCase = true)
            }?.sortedBy { it.lastModified() } ?: emptyList()
            val cutoff = System.currentTimeMillis() - retentionDays * 24 * 60 * 60 * 1000
            val toDelete = backups.filter { it.lastModified() < cutoff }.toMutableSet()
            if (backups.size > retentionCount) {
                toDelete += backups.take((backups.size - retentionCount).toInt())
            }
            toDelete.forEach { file ->
                if (file.canonicalFile.parentFile == backupDir) file.delete()
            }
        }

        val restoreTempDir = File(BACKUP_TEMP_DIR).canonicalFile
        if (restoreTempDir.isDirectory) {
            val cutoff = System.currentTimeMillis() - RESTORE_TEMP_TTL_MILLIS
            restoreTempDir.listFiles { file ->
                file.isFile && file.name.startsWith("restore_") && file.lastModified() < cutoff
            }?.forEach { it.delete() }
        }
    }

    private fun cleanupPendingRestores() {
        val now = System.currentTimeMillis()
        pendingRestores.entries.removeIf {
            if (it.value.expiresAt < now) {
                it.value.zipFile.delete()
                true
            } else {
                false
            }
        }
    }

    private fun trimPendingRestores(currentToken: String) {
        while (pendingRestores.size > MAX_PENDING_RESTORES) {
            val oldest = pendingRestores.entries
                .filter { it.key != currentToken }
                .minByOrNull { it.value.expiresAt }
                ?: break
            pendingRestores.remove(oldest.key)?.zipFile?.delete()
        }
    }

    private fun readEntryLimited(zip: ZipInputStream): ByteArray {
        val output = ByteArrayOutputStream()
        val buffer = ByteArray(8192)
        var total = 0L
        while (true) {
            val read = zip.read(buffer)
            if (read < 0) break
            total += read
            if (total > RequestLimits.MAX_RESTORE_FILE_BYTES) {
                throw PayloadTooLargeException(RequestLimits.MAX_RESTORE_FILE_BYTES)
            }
            output.write(buffer, 0, read)
        }
        return output.toByteArray()
    }

    private fun copyLimited(input: InputStream, output: FileOutputStream) {
        val buffer = ByteArray(8192)
        var total = 0L
        while (true) {
            val read = input.read(buffer)
            if (read < 0) break
            total += read
            if (total > RequestLimits.MAX_RESTORE_ZIP_BYTES) {
                throw PayloadTooLargeException(RequestLimits.MAX_RESTORE_ZIP_BYTES)
            }
            output.write(buffer, 0, read)
        }
    }

    private fun parseBackupFileName(fileName: String): BackupFileEntry? {
        val match = BACKUP_FILE_NAME_PATTERN.matchEntire(fileName) ?: return null
        val postId = match.groupValues[1].toLongOrNull() ?: return null
        val slug = match.groupValues[2]
        val draftName = match.groupValues[4].takeIf { it.isNotEmpty() }
        return BackupFileEntry(
            fileName = fileName,
            postId = postId,
            slug = slug,
            content = "",
            draftName = draftName
        )
    }

    private fun isSafeBackupName(name: String): Boolean {
        if (name.isBlank() || name.length > 512) return false
        if (name.contains('/') || name.contains('\\') || name.contains('\u0000')) return false
        return !name.startsWith("..") && name != "." && name != ".."
    }

    private fun invalidPreview(fileName: String, reason: String) = RestorePreviewFile(
        fileName = fileName,
        postId = null,
        slug = null,
        type = "INVALID",
        action = "INVALID",
        reason = reason
    )

    private fun BackupFileEntry.toPreview(action: String, reason: String? = null) = RestorePreviewFile(
        fileName = fileName,
        postId = postId,
        slug = slug,
        type = if (isDraft) "DRAFT" else "PUBLISHED",
        action = action,
        reason = reason
    )

    private fun envLong(name: String, default: Long): Long {
        val value = System.getenv(name)?.toLongOrNull() ?: return default
        return if (value > 0) value else if (name == "NOLA_BACKUP_INTERVAL_HOURS" && value == 0L) 0 else default
    }

    private data class BackupFileEntry(
        val fileName: String,
        val postId: Long,
        val slug: String,
        val content: String,
        val draftName: String?
    ) {
        val isDraft: Boolean get() = draftName != null
    }

    private data class ParsedBackup(
        val entries: List<BackupFileEntry>,
        val invalidFiles: List<RestorePreviewFile>
    )

    private data class PendingRestore(
        val zipFile: File,
        val expiresAt: Long
    )

    private enum class RestoreConflictStrategy {
        SKIP,
        RENAME
    }

    private companion object {
        const val BACKUP_DIR = ".nola/backup"
        const val BACKUP_TEMP_DIR = ".nola/temp"
        const val DEFAULT_BACKUP_INTERVAL_HOURS = 24L
        const val DEFAULT_RETENTION_DAYS = 30L
        const val DEFAULT_RETENTION_COUNT = 20L
        const val MAX_PENDING_RESTORES = 5
        const val RESTORE_TOKEN_TTL_MILLIS = 15 * 60 * 1000L
        const val RESTORE_TEMP_TTL_MILLIS = 24 * 60 * 60 * 1000L

        val BACKUP_FILE_NAME_PATTERN =
            Regex("""^(\d+)_(.+)_(\d+)(?:_draft_(.+))?\.md$""", RegexOption.IGNORE_CASE)
    }
}
