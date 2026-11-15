package cc.loac.data.responses

/**
 * 备份恢复预览中的单个文件
 * @param fileName 备份文件名
 * @param postId 文件名中解析出的文章 ID
 * @param slug 文件名中解析出的文章别名
 * @param type 文件类型：PUBLISHED / DRAFT / INVALID
 * @param action 预计操作：CREATE / UPDATE_CONTENT / ADD_DRAFT / SKIP / CONFLICT / INVALID
 * @param reason 操作原因或跳过原因
 */
data class RestorePreviewFile(
    val fileName: String,
    val postId: Long?,
    val slug: String?,
    val type: String,
    val action: String,
    val reason: String? = null
)

/**
 * 备份恢复预览响应
 * @param token 恢复令牌，调用应用接口时需携带
 * @param expiresAt 令牌过期时间戳
 * @param totalFiles 备份中的文件数量
 * @param publishedFiles 正文文件数量
 * @param draftFiles 草稿文件数量
 * @param createPostCount 预计新建文章数量
 * @param updatePostCount 预计更新正文的文章数量
 * @param addDraftCount 预计新增草稿数量
 * @param conflictCount 冲突文件数量
 * @param skipCount 预计跳过文件数量
 * @param invalidCount 无法识别的文件数量
 * @param files 文件明细
 * @param warnings 恢复限制说明
 */
data class RestorePreviewResponse(
    val token: String,
    val expiresAt: Long,
    val totalFiles: Int,
    val publishedFiles: Int,
    val draftFiles: Int,
    val createPostCount: Int,
    val updatePostCount: Int,
    val addDraftCount: Int,
    val conflictCount: Int,
    val skipCount: Int,
    val invalidCount: Int,
    val files: List<RestorePreviewFile>,
    val warnings: List<String>
)

/**
 * 备份恢复执行结果
 * @param createdPosts 新建文章数量
 * @param updatedPosts 更新正文的文章数量
 * @param addedDrafts 新增草稿数量
 * @param skippedFiles 跳过文件数量
 * @param failedFiles 失败文件数量
 * @param failures 失败原因
 */
data class RestoreResultResponse(
    val createdPosts: Int,
    val updatedPosts: Int,
    val addedDrafts: Int,
    val skippedFiles: Int,
    val failedFiles: Int,
    val failures: List<String>
)
