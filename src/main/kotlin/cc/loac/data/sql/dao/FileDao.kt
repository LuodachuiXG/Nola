package cc.loac.data.sql.dao

import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.FileWithGroup
import cc.loac.data.models.MFile
import cc.loac.data.models.enums.FileSort
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.responses.FileResponse
import cc.loac.data.responses.Pager

/**
 * 文件表操作接口
 */
interface FileDao {

    /**
     * 设置文件存储方式配置
     * @param storageMode 文件存储方式
     * @param config 配置
     */
    suspend fun setFileStorageConfig(storageMode: FileStorageModeEnum, config: String): Boolean

    /**
     * 删除文件存储方式
     * @param storageMode 文件存储方式
     */
    suspend fun deleteFileStorageConfig(storageMode: FileStorageModeEnum): Boolean

    /**
     * 获取文件存储方式配置
     * @param storageMode 文件存储方式
     */
    suspend fun getFileStorageConfig(storageMode: FileStorageModeEnum): String?

    /**
     * 获取指定的存储方式的文件数量
     * @param storageMode 文件存储方式
     */
    suspend fun getFileCount(storageMode: FileStorageModeEnum): Long

    /**
     * 获取指定的文件组下的文件数量
     * @param fileGroupId 文件组 ID
     */
    suspend fun getFileCount(fileGroupId: Int): Long

    /**
     * 获取总文件数量
     */
    suspend fun getFileCount(): Long

    /**
     * 获取所有已经设置过的存储策略
     */
    suspend fun getModes(): List<FileStorageModeEnum>

    /**
     * 添加文件组
     * @param fileGroup 文件组数据类
     */
    suspend fun addFileGroup(fileGroup: FileGroup): FileGroup?

    /**
     * 删除文件组
     * @param fileGroupId 文件组 ID
     */
    suspend fun deleteFileGroup(fileGroupId: Int): Boolean

    /**
     * 修改文件组
     * 文件组存储方式不能修改
     * @param fileGroup 文件组修改请求数据类
     */
    suspend fun updateFileGroup(fileGroup: FileGroupUpdateRequest): Boolean

    /**
     * 根据文件组 ID 获取文件组
     * @param fileGroupId 文件组 ID
     */
    suspend fun getFileGroup(fileGroupId: Int): FileGroup?

    /**
     * 根据文件存储方式获取文件组
     * @param fileStorageMode 文件存储方式（为 null 就获取所有文件组）
     */
    suspend fun getFileGroups(fileStorageMode: FileStorageModeEnum?): List<FileGroup>

    /**
     * 添加文件
     * @param mFile 文件数据类
     */
    suspend fun addFile(mFile: MFile): MFile?

    /**
     * 删除文件
     * @param fileId 文件 ID
     */
    suspend fun deleteFile(fileId: Int): Boolean

    /**
     * 根据文件名数组和文件存储方式批量删除文件
     * @param fileNames 文件名集合
     * @param storageMode 文件存储方式
     */
    suspend fun deleteFile(fileNames: List<String>, storageMode: FileStorageModeEnum): Boolean

    /**
     * 根据文件 ID 数组批量删除文件
     * @param fileIds 文件 ID 集合
     */
    suspend fun deleteFile(fileIds: List<Int>): Boolean

    /**
     * 修改文件
     * @param mFile 文件数据类
     */
    suspend fun updateFile(mFile: MFile): Boolean

    /**
     * 批量修改文件
     * @param files 文件集合
     */
    suspend fun updateFiles(files: List<MFile>): Boolean

    /**
     * 根据文件名、文件组 ID 和文件存储方式获取文件
     * @param fileName 文件名
     * @param fileGroupId 文件组 ID
     * @param storageMode 文件存储方式
     */
    suspend fun getFile(
        fileName: String,
        fileGroupId: Int?,
        storageMode: FileStorageModeEnum
    ): MFile?

    /**
     * 根据文件 ID 集合获取文件和文件组数据类
     * @param ids 文件 ID 集合
     */
    suspend fun getFileWithGroups(
        ids: List<Int>
    ): List<FileWithGroup>

    /**
     * 分页获取文件和文件组数据
     * @param page 当前页（0 获取所有文件）
     * @param size 每页条数
     * @param sort 排序方式
     * @param mode 文件存储方式
     * @param key 关键字
     */
    suspend fun getFileWithGroups(
        page: Int,
        size: Int,
        sort: FileSort?,
        mode: FileStorageModeEnum?,
        key: String?
    ): Pager<FileWithGroup>

    /**
     * 获取所有文件
     * @param fileIds 文件 ID 集合
     */
    suspend fun getFiles(fileIds: List<Int>): List<MFile>

}