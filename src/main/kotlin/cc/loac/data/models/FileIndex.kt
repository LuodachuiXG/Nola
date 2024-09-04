package cc.loac.data.models

import cc.loac.data.models.enums.FileStorageModeEnum
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * 文件索引数据类
 * 用于准确标记数据库中不同存储类型的文件
 * @param fileId 文件 ID
 * @param name 文件名（文件名是分组名加上文件名字，如：/img/1.jpg）
 * @param storageMode 文件存储方式
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class FileIndex(
    val fileId: Long? = null,
    val name: String,
    val storageMode: FileStorageModeEnum
)
