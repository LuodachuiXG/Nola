package cc.loac.data.sql.tables

import cc.loac.data.models.enums.FileStorageType
import org.jetbrains.exposed.sql.Table

/**
 * 文件表（包括本地存储和云对象存储的文件）
 */
object Files : Table("files") {
    /** 文件 ID **/
    val fileId = integer("fileId").autoIncrement()
    /** 文件组 ID **/
    val fileGroupId = integer("fileGroupId").nullable()
    /** 文件地址 **/
    val path = varchar("path", 512)
    /** 文件名 **/
    val displayName = varchar("displayName", 512)
    /** 文件大小**/
    val size = long("size")
    /** 文件存储方式 **/
    val storageType = enumerationByName("storageType", 24, FileStorageType::class)
    /** 最后修改时间戳 **/
    val lastModifyTime = long("lastModifyTime").nullable()
    /** 创建时间戳 **/
    val createTime = long("createTime")

    override val primaryKey = PrimaryKey(fileId)
}