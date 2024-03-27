package cc.loac.data.sql.dao.impl

import cc.loac.data.models.FileGroup
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.FileDao
import cc.loac.data.sql.tables.FileGroups
import cc.loac.data.sql.tables.FileStorageModes
import cc.loac.data.sql.tables.Files
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 文件表操作接口实现类
 */
class FileDaoImpl : FileDao {

    /**
     * 将 ResultRow 转换为 FileGroup
     * @param row ResultRow
     */
    private fun resultToFileGroup(row: ResultRow): FileGroup {
        return FileGroup(
            fileGroupId = row[FileGroups.fileGroupId],
            displayName = row[FileGroups.displayName],
            path = row[FileGroups.path],
            storageMode = row[FileGroups.storageMode]
        )
    }

    /**
     * 设置文件存储方式配置
     * @param storageMode 文件存储方式
     * @param config 配置
     */
    override suspend fun setFileStorageConfig(
        storageMode: FileStorageModeEnum,
        config: String
    ): Boolean = dbQuery {
        if (getFileStorageConfig(storageMode) == null) {
            // 新增配置
            FileStorageModes.insert {
                it[FileStorageModes.storageMode] = storageMode
                it[FileStorageModes.config] = config
            }.resultedValues?.singleOrNull() != null
        } else {
            // 修改配置
            FileStorageModes.update({
                FileStorageModes.storageMode eq storageMode
            }) {
                it[FileStorageModes.config] = config
            } > 0
        }
    }

    /**
     * 删除文件存储方式
     * @param storageMode 文件存储方式
     */
    override suspend fun deleteFileStorageConfig(storageMode: FileStorageModeEnum): Boolean = dbQuery {
        FileStorageModes.deleteWhere {
            FileStorageModes.storageMode eq storageMode
        } > 0
    }

    /**
     * 获取文件存储方式配置
     * @param storageMode 文件存储方式
     */
    override suspend fun getFileStorageConfig(
        storageMode: FileStorageModeEnum
    ): String? = dbQuery {
        FileStorageModes
            .selectAll()
            .where {
                FileStorageModes.storageMode eq storageMode
            }.firstOrNull()?.let { it[FileStorageModes.config] }
    }

    /**
     * 获取指定的存储方式的文件数量
     * @param storageMode 文件存储方式
     */
    override suspend fun getFileCount(storageMode: FileStorageModeEnum): Long = dbQuery {
        Files.selectAll().where {
            Files.storageMode eq storageMode
        }.count()
    }

    /**
     * 获取指定的文件组下的文件数量
     * @param fileGroupId 文件组 ID
     */
    override suspend fun getFileCount(fileGroupId: Int): Long = dbQuery {
        Files.selectAll().where {
            Files.fileGroupId eq fileGroupId
        }.count()
    }

    /**
     * 获取总文件数量
     */
    override suspend fun getFileCount(): Long = dbQuery {
        Files.selectAll().count()
    }

    /**
     * 获取所有已经设置过的存储策略
     */
    override suspend fun getModes(): List<FileStorageModeEnum> = dbQuery {
        FileStorageModes.selectAll().map {
            it[FileStorageModes.storageMode]
        }
    }

    /**
     * 添加文件组
     * @param fileGroup 文件组数据类
     */
    override suspend fun addFileGroup(fileGroup: FileGroup): FileGroup? = dbQuery {
        FileGroups.insert {
            it[displayName] = fileGroup.displayName
            it[path] = fileGroup.path
            it[storageMode] = fileGroup.storageMode
        }.resultedValues?.singleOrNull()?.let(::resultToFileGroup)
    }

    /**
     * 删除文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun deleteFileGroup(fileGroupId: Int): Boolean = dbQuery {
        FileGroups.deleteWhere {
            FileGroups.fileGroupId eq fileGroupId
        } > 0
    }

    /**
     * 修改文件组
     * 文件组存储方式不能修改
     * @param fileGroup 文件组修改请求数据类
     */
    override suspend fun updateFileGroup(fileGroup: FileGroupUpdateRequest): Boolean = dbQuery {
        FileGroups.update({
            FileGroups.fileGroupId eq fileGroup.fileGroupId
        }) {
            it[displayName] = fileGroup.displayName
            it[path] = fileGroup.path
        } > 0
    }

    /**
     * 根据文件组 ID 获取文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun getFileGroup(fileGroupId: Int): FileGroup? = dbQuery {
        FileGroups.selectAll().where {
            FileGroups.fileGroupId eq fileGroupId
        }.map(::resultToFileGroup).singleOrNull()
    }

    /**
     * 根据文件存储方式获取文件组
     * @param fileStorageMode 文件存储方式（为 null 就获取所有文件组）
     */
    override suspend fun getFileGroups(
        fileStorageMode: FileStorageModeEnum?
    ): List<FileGroup> = dbQuery {
        val baseQuery = FileGroups.selectAll()
        if (fileStorageMode != null) {
            baseQuery.andWhere {
                FileGroups.storageMode eq fileStorageMode
            }
        }
        baseQuery.map(::resultToFileGroup)
    }
}