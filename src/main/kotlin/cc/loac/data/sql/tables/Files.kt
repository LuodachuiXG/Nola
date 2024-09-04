package cc.loac.data.sql.tables

import cc.loac.data.models.enums.FileStorageModeEnum
import org.jetbrains.exposed.sql.Table

/**
 * 文件表（包括本地存储和云对象存储的文件）
 */
object Files : Table("file") {
    /** 文件 ID **/
    val fileId = long("file_id").autoIncrement()

    /** 文件组 ID **/
    val fileGroupId = long("file_group_id").nullable()

    /** 文件名 **/
    val displayName = varchar("display_name", 512)

    /** 文件大小 **/
    val size = long("size")

    /** 文件存储方式 **/
    val storageMode = enumerationByName("storage_mode", 48, FileStorageModeEnum::class)

    /** 创建时间戳 **/
    val createTime = long("create_time")

    override val primaryKey = PrimaryKey(fileId)
}