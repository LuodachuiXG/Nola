package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.models.FileGroup
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.sql.dao.FileDao
import cc.loac.services.FileService
import cc.loac.utils.jsonToClass
import cc.loac.utils.toJSONString
import org.koin.java.KoinJavaComponent.inject

/**
 * 文件服务接口实现类
 */
class FileServiceImpl : FileService {

    private val fileDao: FileDao by inject(FileDao::class.java)

    /**
     * 设置腾讯云对象存储配置
     * @param config 腾讯云对象存储配置
     */
    override suspend fun setTencentCOS(config: TencentCOSConfig): Boolean {
        return fileDao.setFileStorageConfig(FileStorageModeEnum.TENCENT_COS, config.toJSONString())
    }

    /**
     * 删除腾讯云对象存储配置
     */
    override suspend fun deleteTencentCOS(): Boolean {
        // 先判断腾讯云对象存储下是否还有文件
        val fileCount = fileDao.getFileCount(FileStorageModeEnum.TENCENT_COS)
        if (fileCount > 0) throw MyException("腾讯云对象存储策略下还有 $fileCount 个文件，无法删除")
        return fileDao.deleteFileStorageConfig(FileStorageModeEnum.TENCENT_COS)
    }

    /**
     * 获取腾讯云对象存储配置
     */
    override suspend fun getTencentCOS(): TencentCOSConfig? {
        val configStr = fileDao.getFileStorageConfig(FileStorageModeEnum.TENCENT_COS) ?: return null
        return configStr.jsonToClass<TencentCOSConfig>()
    }

    /**
     * 获取所有已经设置过的存储策略，
     * 默认包含本地存储 (LOCAL)
     */
    override suspend fun getModes(): List<FileStorageModeEnum> {
        val modes = mutableListOf(FileStorageModeEnum.LOCAL)
        modes.addAll(fileDao.getModes())
        return modes
    }

    /**
     * 添加文件组
     * @param fileGroup 文件组数据类
     */
    override suspend fun addFileGroup(fileGroup: FileGroup): FileGroup? {
        // 如果文件存储方式不是本地存储，就先检查对应的存储方式是否已经设置
        if (fileGroup.storageMode != FileStorageModeEnum.LOCAL) {
            if (fileDao.getFileStorageConfig(fileGroup.storageMode) == null) {
                throw MyException("文件存储策略 [${fileGroup.storageMode}] 还未设置")
            }
        }

        // 添加前先判断相同存储方式下，文件组名和文件组路径是否重复
        if (getFileGroupByDisplayName(fileGroup.storageMode, fileGroup.displayName) != null) {
            throw MyException("文件组名 [${fileGroup.displayName}}] 已经存在")
        }

        if (getFileGroupByPath(fileGroup.storageMode, fileGroup.path) != null) {
            throw MyException("文件组路径 [${fileGroup.path}}] 已经存在")
        }

        // 添加文件组
        return fileDao.addFileGroup(fileGroup)
    }

    /**
     * 删除文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun deleteFileGroup(fileGroupId: Int): Boolean {
        // 删除前先判断当前文件组下是否还有文件
        val fileCount = fileDao.getFileCount(fileGroupId)
        if (fileCount > 0) throw MyException("文件组下还有 $fileCount 个文件，无法删除")
        return fileDao.deleteFileGroup(fileGroupId)
    }

    /**
     * 修改文件组
     * 文件组存储方式不能修改
     * @param fileGroup 文件组修改请求数据类
     */
    override suspend fun updateFileGroup(fileGroup: FileGroupUpdateRequest): Boolean {
        // 先获取要修改的文件组
        val oldFileGroup = getFileGroup(fileGroup.fileGroupId) ?: return false

        // 如果文件组名已经存在，并且不是自己的话，抛出异常
        var fg = getFileGroupByDisplayName(oldFileGroup.storageMode, fileGroup.displayName)
        if (fg != null && fg.fileGroupId != fileGroup.fileGroupId) {
            throw MyException("文件组名 [${fileGroup.displayName}}] 已经存在")
        }

        // 如果文件组路径已经存在，并且不是自己的话，抛出异常
        fg = getFileGroupByPath(oldFileGroup.storageMode, fileGroup.path)
        if (fg != null && fg.fileGroupId != fileGroup.fileGroupId) {
            throw MyException("文件组路径 [${fileGroup.path}}] 已经存在")
        }

        /**
         * 如果修改了文件组的地址的话，还要去修改文件夹的名称！！！
         * 这里后续需要添加处理代码
         */


        // 修改文件组
        return fileDao.updateFileGroup(fileGroup)
    }

    /**
     * 根据文件组 ID 获取文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun getFileGroup(fileGroupId: Int): FileGroup? {
        return fileDao.getFileGroup(fileGroupId)
    }

    /**
     * 根据文件存储方式获取文件组
     * @param fileStorageMode 文件存储方式（为 null 就获取所有文件组）
     */
    override suspend fun getFileGroups(fileStorageMode: FileStorageModeEnum?): List<FileGroup> {
        return fileDao.getFileGroups(fileStorageMode)
    }

    /**
     * 根据文件存储方式和文件组名获取文件组
     * @param fileStorageMode 文件存储方式
     * @param displayName 文件组名
     */
    override suspend fun getFileGroupByDisplayName(
        fileStorageMode: FileStorageModeEnum,
        displayName: String
    ): FileGroup? {
        return fileDao.getFileGroups(fileStorageMode).find { it.displayName == displayName }
    }

    /**
     * 根据文件存储方式和文件组路径获取文件组
     * @param fileStorageMode 文件存储方式
     * @param path 文件组路径
     */
    override suspend fun getFileGroupByPath(
        fileStorageMode: FileStorageModeEnum,
        path: String
    ): FileGroup? {
        return fileDao.getFileGroups(fileStorageMode).find { it.path == path }
    }


}