package cc.loac.data.models

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件和文件组数据类
 * @param fileId        文件 ID
 * @param fileGroupId   文件组 ID
 * @param fileName      文件名
 * @param fileGroupName 文件组名
 * @param fileGroupPath 文件组路径
 * @param size          文件大小
 * @param storageMode   文件存储方式
 * @param createTime    文件创建时间
 */
data class FileWithGroup(
    val fileId: Long,
    val fileGroupId: Long?,
    val fileName: String,
    val fileGroupName: String?,
    val fileGroupPath: String?,
    val size: Long,
    val storageMode: FileStorageModeEnum,
    val createTime: Long
)
