package cc.loac.plugins

import cc.loac.services.BackupService
import io.ktor.server.application.*
import org.koin.java.KoinJavaComponent.inject

/**
 * 启动定时备份任务
 * 依赖 Koin 初始化完成，必须在 configureKoin 之后调用
 */
fun Application.configureBackupScheduler() {
    val backupService: BackupService by inject(BackupService::class.java)
    backupService.startScheduler()
}
