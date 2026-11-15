package cc.loac.utils

/**
 * 请求相关的统一大小限制
 */
object RequestLimits {
    /** 分页接口每页最大条数 */
    const val MAX_PAGE_SIZE = 100

    /** 普通 JSON / 文本请求体最大字节数（2 MB） */
    const val MAX_JSON_BODY_BYTES = 2L * 1024 * 1024

    /** multipart 请求体最大字节数（64 MB） */
    const val MAX_MULTIPART_BODY_BYTES = 64L * 1024 * 1024

    /** 单个上传文件最大字节数（50 MB） */
    const val MAX_UPLOAD_FILE_BYTES = 50L * 1024 * 1024

    /** 文章备份一次最多导入的文件数 */
    const val MAX_BACKUP_IMPORT_FILES = 100

    /** 单个备份文件最大字节数（5 MB） */
    const val MAX_BACKUP_IMPORT_FILE_BYTES = 5L * 1024 * 1024

    /** 一次导入的备份文件总大小上限（20 MB） */
    const val MAX_BACKUP_IMPORT_TOTAL_BYTES = 20L * 1024 * 1024

    /** 备份恢复压缩包大小上限（60 MB） */
    const val MAX_RESTORE_ZIP_BYTES = 60L * 1024 * 1024

    /** 备份恢复压缩包内文件数量上限 */
    const val MAX_RESTORE_ENTRIES = 1000

    /** 备份恢复单个文件解压后大小上限（5 MB） */
    const val MAX_RESTORE_FILE_BYTES = 5L * 1024 * 1024

    /** 备份恢复所有文件解压后总大小上限（60 MB） */
    const val MAX_RESTORE_TOTAL_BYTES = 60L * 1024 * 1024
}
