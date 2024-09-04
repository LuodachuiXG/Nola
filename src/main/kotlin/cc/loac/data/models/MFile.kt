package cc.loac.data.models

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件数据类
 * @param fileId 文件 ID
 * @param fileGroupId 文件组 ID
 * @param displayName 文件名
 * @param size 文件大小
 * @param storageMode 文件存储方式
 * @param createTime 创建时间戳
 */
data class MFile(
    val fileId: Long,
    val fileGroupId: Long?,
    val displayName: String,
    val size: Long,
    val storageMode: FileStorageModeEnum,
    val createTime: Long
)
