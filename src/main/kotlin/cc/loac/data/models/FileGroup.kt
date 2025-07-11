package cc.loac.data.models

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件组数据类
 * @param fileGroupId 文件组 ID
 * @param displayName 文件组名
 * @param path 文件组路径
 * @param storageMode 文件存储方式
 */
data class FileGroup(
    val fileGroupId: Long,
    val displayName: String,
    val path: String,
    val storageMode: FileStorageModeEnum
)
