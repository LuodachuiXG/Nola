package cc.loac.data.models

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件存储方式数据类
 * @param fileStorageModeId 文件存储方式 ID
 * @param storageMode 文件存储方式
 * @param config 配置
 */
data class FileStorageMode(
    val fileStorageModeId: Int,
    val storageMode: FileStorageModeEnum,
    val config: String
)
