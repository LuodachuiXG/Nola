package cc.loac.services

import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest

/**
 * 文件服务接口
 */
interface FileService {
    /**
     * 设置腾讯云对象存储配置
     * @param config 腾讯云对象存储配置
     */
    suspend fun setTencentCOS(config: TencentCOSConfig): Boolean

    /**
     * 删除腾讯云对象存储配置
     */
    suspend fun deleteTencentCOS(): Boolean

    /**
     * 获取腾讯云对象存储配置
     */
    suspend fun getTencentCOS(): TencentCOSConfig?

    /**
     * 获取所有已经设置过的存储策略，
     * 默认包含本地存储 (LOCAL)
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
     * 根据文件存储方式和文件组名获取文件组
     * @param fileStorageMode 文件存储方式
     * @param displayName 文件组名
     */
    suspend fun getFileGroupByDisplayName(
        fileStorageMode: FileStorageModeEnum,
        displayName: String
    ): FileGroup?

    /**
     * 根据文件存储方式和文件组路径获取文件组
     * @param fileStorageMode 文件存储方式
     * @param path 文件组路径
     */
    suspend fun getFileGroupByPath(
        fileStorageMode: FileStorageModeEnum,
        path: String
    ): FileGroup?
}