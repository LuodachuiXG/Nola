package cc.loac.services

import cc.loac.data.responses.ExportPostResponse
import cc.loac.data.responses.RestorePreviewResponse
import cc.loac.data.responses.RestoreResultResponse
import java.io.InputStream

/**
 * 备份服务接口
 */
interface BackupService {

    /**
     * 导出文章备份并执行保留策略
     */
    suspend fun exportPosts(): ExportPostResponse

    /**
     * 暂存并预览待恢复的备份压缩包
     * @param inputStream 备份压缩包输入流
     */
    suspend fun stageRestore(inputStream: InputStream): RestorePreviewResponse

    /**
     * 应用已预览的备份恢复
     * @param token 预览令牌
     * @param conflictStrategy 冲突处理策略
     */
    suspend fun applyRestore(token: String, conflictStrategy: String? = null): RestoreResultResponse

    /**
     * 启动定时备份任务（由 Application 在 Koin 初始化后调用）
     */
    fun startScheduler()
}
