package cc.loac.data.sql.tables

import cc.loac.data.models.enums.FileStorageType
import org.jetbrains.exposed.sql.Table


/**
 * 文件组表
 */
object FileGroup : Table("file_group") {

    /** 文件组 ID **/
    val fileGroupId = integer("fileGroupId").autoIncrement()

    /** 文件组名 **/
    val displayName = varchar("displayName", 128)

    /** 文件存储方式 **/
    val storageType = enumerationByName("storageType", 24, FileStorageType::class)

    override val primaryKey = PrimaryKey(fileGroupId)
}