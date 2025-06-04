package cc.loac.services

import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.enums.FileSort
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.requests.FileMoveRequest
import cc.loac.data.requests.FileRecordRequest
import cc.loac.data.responses.FileResponse
import cc.loac.data.responses.Pager
import java.io.InputStream

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
    suspend fun deleteFileGroup(fileGroupId: Long): Boolean

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
    suspend fun getFileGroup(fileGroupId: Long): FileGroup?

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


    /**
     * 上传文件
     * @param inputStream 文件二进制数据
     * @param fileName 文件名
     * @param storageMode 文件存储方式
     * @param fileGroupId 文件组 ID
     * @param fileLength 文件长度
     */
    suspend fun uploadFile(
        inputStream: InputStream,
        fileName: String,
        storageMode: FileStorageModeEnum,
        fileGroupId: Long?,
        fileLength: Long?
    ): FileResponse?

    /**
     * 添加上传文件记录
     * @param fileRecord 文件记录请求类
     */
    suspend fun uploadFileRecord(
        fileRecord: FileRecordRequest
    ): FileResponse?

    /**
     * 根据文件 ID 删除文件
     * @param ids 文件 ID 数组
     * @return 删除成功的文件 ID 数组
     */
    suspend fun deleteFiles(ids: List<Long>): List<Long>

    /**
     * 根据文件索引删除文件
     * @param fileIndexes 文件索引数组
     * @return 删除成功的文件索引数组
     */
    suspend fun deleteFilesByFineIndexes(fileIndexes: List<FileIndex>): List<FileIndex>

    /**
     * 移动文件
     * @param fileMoveRequest 文件移动请求数据类
     * @return 移动成功旧文件的地址集合
     */
    suspend fun moveFiles(fileMoveRequest: FileMoveRequest): List<String>

    /**
     * 获取文件
     * @param page 当前页
     * @param size 每页条数
     * @param sort 排序方式
     * @param mode 文件存储方式
     * @param groupId 文件组 ID
     * @param key 关键字
     */
    suspend fun getFiles(
        page: Int,
        size: Int,
        sort: FileSort? = null,
        mode: FileStorageModeEnum? = null,
        groupId: Long? = null,
        key: String? = null
    ): Pager<FileResponse>


    /**
     * 获取文件数量
     */
    suspend fun fileCount(): Long
}