package cc.loac.data.sql.tables

import cc.loac.data.models.enums.FileStorageModeEnum
import org.jetbrains.exposed.sql.Table

/**
 * 文件存储方式表
 */
object FileStorageModes : Table("file_storage_modes") {
    /** 文件存储方式 ID **/
    val fileStorageModeId = integer("fileStorageModeId").autoIncrement()

    /** 文件存储方式 **/
    val storageMode = enumerationByName("storageMode", 24, FileStorageModeEnum::class)

    /** 配置 **/
    val config = text("config")

    override val primaryKey = PrimaryKey(fileStorageModeId)
}