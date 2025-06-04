package cc.loac.data.requests

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件记录请求数据类
 * @param name 文件名（含后缀）
 * @param size 文件大小（字节 Bytes）
 * @param storageMode 文件存储策略（为 null 默认本地存储 LOCAL）
 * @param fileGroupId 文件组 ID（为 null 默认不分组）
 */
data class FileRecordRequest(
    val name: String,
    val size: Long,
    val storageMode: FileStorageModeEnum? = null,
    val fileGroupId: Long? = null
)