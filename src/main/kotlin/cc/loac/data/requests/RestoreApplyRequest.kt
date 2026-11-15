package cc.loac.data.requests

/**
 * 应用备份恢复请求
 * @param token 预览接口返回的恢复令牌
 * @param conflictStrategy 冲突处理策略：SKIP（默认）或 RENAME
 */
data class RestoreApplyRequest(
    val token: String,
    val conflictStrategy: String? = null
)
