package cc.loac.data.sql.tables

import cc.loac.data.models.enums.FileStorageModeEnum
import org.jetbrains.exposed.sql.Table


/**
 * 文件组表
 */
object FileGroups : Table("file_group") {

    /** 文件组 ID **/
    val fileGroupId = integer("fileGroupId").autoIncrement()

    /** 文件组名 **/
    val displayName = varchar("displayName", 128)

    /** 文件组路径 **/
    val path = varchar("path", 128)

    /** 文件存储方式 **/
    val storageMode = enumerationByName("storageMode", 48, FileStorageModeEnum::class)

    override val primaryKey = PrimaryKey(fileGroupId)
}