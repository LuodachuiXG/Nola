package cc.loac.data.sql.dao

import cc.loac.data.models.FileGroup
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest

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

}