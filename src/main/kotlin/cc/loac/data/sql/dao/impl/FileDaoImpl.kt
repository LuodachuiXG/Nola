package cc.loac.data.sql.dao.impl

import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.FileWithGroup
import cc.loac.data.models.MFile
import cc.loac.data.models.enums.FileSort
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.responses.FileResponse
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.FileDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.FileGroups
import cc.loac.data.sql.tables.FileStorageModes
import cc.loac.data.sql.tables.Files
import cc.loac.utils.formatSlash
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
     * 将 ResultRow 转换为 MFile
     * @param row ResultRow
     */
    private fun resultToMFile(row: ResultRow): MFile {
        return MFile(
            fileId = row[Files.fileId],
            fileGroupId = row[Files.fileGroupId],
            displayName = row[Files.displayName],
            size = row[Files.size],
            storageMode = row[Files.storageMode],
            createTime = row[Files.createTime]
        )
    }

    /**
     * 将 ResultRow 转换为 FileWithGroup 文件和文件组数据类
     * @param row ResultRow
     */
    private fun resultToFileWithGroup(row: ResultRow): FileWithGroup {
        return FileWithGroup(
            fileId = row[Files.fileId],
            fileGroupId = row[Files.fileGroupId],
            fileName = row[Files.displayName],
            fileGroupName = row[FileGroups.displayName],
            fileGroupPath = row[FileGroups.path],
            size = row[Files.size],
            storageMode = row[Files.storageMode],
            createTime = row[Files.createTime]

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
        val result = FileStorageModes.deleteWhere {
            FileStorageModes.storageMode eq storageMode
        } > 0
        // 删除文件组
        FileGroups.deleteWhere {
            FileGroups.storageMode eq storageMode
        }
        result
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
    override suspend fun getFileCount(fileGroupId: Long): Long = dbQuery {
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
            it[path] = fileGroup.path.formatSlash()
            it[storageMode] = fileGroup.storageMode
        }.resultedValues?.singleOrNull()?.let(::resultToFileGroup)
    }

    /**
     * 删除文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun deleteFileGroup(fileGroupId: Long): Boolean = dbQuery {
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
        } > 0
    }

    /**
     * 根据文件组 ID 获取文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun getFileGroup(fileGroupId: Long): FileGroup? = dbQuery {
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
        val baseQuery = FileGroups.selectAll().orderBy(FileGroups.storageMode)
        if (fileStorageMode != null) {
            baseQuery.andWhere {
                FileGroups.storageMode eq fileStorageMode
            }
        }
        baseQuery.map(::resultToFileGroup)
    }

    /**
     * 添加文件
     * @param mFile 文件数据类
     */
    override suspend fun addFile(mFile: MFile): MFile? = dbQuery {
        Files.insert {
            if (mFile.fileGroupId != null) {
                it[fileGroupId] = mFile.fileGroupId
            }
            it[displayName] = mFile.displayName
            it[size] = mFile.size
            it[storageMode] = mFile.storageMode
            it[createTime] = mFile.createTime
        }.resultedValues?.singleOrNull()?.let(::resultToMFile)
    }

    /**
     * 删除文件
     * @param fileId 文件 ID
     */
    override suspend fun deleteFile(fileId: Long): Boolean = dbQuery {
        Files.deleteWhere {
            Files.fileId eq fileId
        } > 0
    }

    /**
     * 根据文件名数组和文件存储方式批量删除文件
     * @param fileNames 文件名集合
     * @param storageMode 文件存储方式
     */
    override suspend fun deleteFile(fileNames: List<String>, storageMode: FileStorageModeEnum): Boolean = dbQuery {
        Files.deleteWhere {
            displayName inList fileNames and (Files.storageMode eq storageMode)
        } > 0
    }


    /**
     * 批量删除文件
     * @param fileIds 文件 ID 集合
     */
    override suspend fun deleteFile(fileIds: List<Long>): Boolean = dbQuery {
        Files.deleteWhere {
            fileId inList fileIds
        } > 0
    }

    /**
     * 修改文件
     * @param mFile 文件数据类
     */
    override suspend fun updateFile(mFile: MFile): Boolean = dbQuery {
        Files.update({
            Files.fileId eq mFile.fileId
        }) {
            it[fileGroupId] = mFile.fileGroupId
            it[displayName] = mFile.displayName
            it[size] = mFile.size
            it[storageMode] = mFile.storageMode
        } > 0
    }

    /**
     * 批量修改文件
     * @param files 文件集合
     */
    override suspend fun updateFiles(files: List<MFile>): Boolean = dbQuery {
        var result = 0
        files.forEach { file ->
            Files.update({
                Files.fileId eq file.fileId
            }) {
                it[fileGroupId] = file.fileGroupId
                it[displayName] = file.displayName
                it[size] = file.size
                it[storageMode] = file.storageMode
            }.let { if (it > 0) result++ }
        }
        result > 0
    }

    /**
     * 根据文件名、文件组 ID 和文件存储方式获取文件
     * @param fileName 文件名
     * @param fileGroupId 文件组 ID
     * @param storageMode 文件存储方式
     */
    override suspend fun getFile(
        fileName: String,
        fileGroupId: Long?,
        storageMode: FileStorageModeEnum
    ): MFile? = dbQuery {
        val baseQuery = Files.selectAll()
        if (fileGroupId != null) {
            baseQuery.andWhere {
                Files.fileGroupId eq fileGroupId
            }
        }
        baseQuery.andWhere {
            Files.displayName eq fileName and
                    (Files.storageMode eq storageMode)
        }
        baseQuery
            .map(::resultToMFile)
            .singleOrNull()
    }

    /**
     * 根据文件 ID 集合获取文件和文件组数据类
     * @param ids 文件 ID 集合
     */
    override suspend fun getFileWithGroups(ids: List<Long>): List<FileWithGroup> = dbQuery {
        Files.leftJoin(
            FileGroups,
            additionalConstraint = { Files.fileGroupId eq FileGroups.fileGroupId }
        )
            .selectAll()
            .where { Files.fileId inList ids }
            .map(::resultToFileWithGroup)
    }

    /**
     * 获取文件
     * @param page 当前页
     * @param size 每页条数
     * @param sort 排序方式
     * @param mode 文件存储方式
     * @param groupId 文件组 ID
     * @param key 关键字
     */
    override suspend fun getFileWithGroups(
        page: Int,
        size: Int,
        sort: FileSort?,
        mode: FileStorageModeEnum?,
        groupId: Long?,
        key: String?
    ): Pager<FileWithGroup> {
        val query = Files.leftJoin(
            FileGroups,
            additionalConstraint = { Files.fileGroupId eq FileGroups.fileGroupId }
        ).selectAll()

        if (mode != null) {
            query.andWhere { Files.storageMode eq mode }
        }

        if (groupId != null) {
            query.andWhere { Files.fileGroupId eq groupId }
        }

        if (!key.isNullOrBlank()) {
            query.andWhere {
                Files.displayName like "%$key%"
            }
        }

        when (sort) {
            FileSort.CREATE_TIME_DESC -> query.orderBy(Files.createTime, SortOrder.DESC)
            FileSort.CREATE_TIME_ASC -> query.orderBy(Files.createTime, SortOrder.ASC)
            FileSort.SIZE_DESC -> query.orderBy(Files.size, SortOrder.DESC)
            FileSort.SIZE_ASC -> query.orderBy(Files.size, SortOrder.ASC)
            else -> {}
        }

        if (page == 0) {
            // 获取所有文件
            val files = dbQuery { query.map(::resultToFileWithGroup) }
            return Pager(0, 0, files, files.size.toLong(), 1)
        }
        // page 不等于 0，分页查询
        return Files.startPage(page, size, ::resultToFileWithGroup) { query }
    }

    /**
     * 获取所有文件
     * @param fileIds 文件 ID 集合
     */
    override suspend fun getFiles(fileIds: List<Long>): List<MFile> = dbQuery {
        Files.selectAll().where { Files.fileId inList fileIds }.map(::resultToMFile)
    }
}