package cc.loac.data.responses

import cc.loac.data.models.enums.FileStorageModeEnum

/**
 * 文件响应数据类
 * @param fileId 文件 ID
 * @param fileGroupId 文件组 ID
 * @param fileGroupName 文件组名
 * @param displayName 文件名
 * @param url 文件地址
 * @param size 文件大小
 * @param storageMode 文件存储方式
 * @param createTime 文件创建时间戳
 */
data class FileResponse(
    val fileId: Int,
    val fileGroupId: Int?,
    val fileGroupName: String?,
    val displayName: String,
    val url: String,
    val size: Long,
    val storageMode: FileStorageModeEnum,
    val createTime: Long
)
