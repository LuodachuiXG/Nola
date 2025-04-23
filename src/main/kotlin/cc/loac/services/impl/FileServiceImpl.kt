package cc.loac.services.impl

import cc.loac.data.exceptions.FileStorageNotConfiguredException
import cc.loac.data.exceptions.MyException
import cc.loac.data.files.FileOption
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.data.files.impl.LocalFileStorageImpl
import cc.loac.data.files.impl.TencentCOSImpl
import cc.loac.data.files.impl.URL_STORAGE_PATH
import cc.loac.data.models.FileGroup
import cc.loac.data.models.FileIndex
import cc.loac.data.models.FileWithGroup
import cc.loac.data.models.MFile
import cc.loac.data.models.enums.FileSort
import cc.loac.data.models.enums.FileStorageModeEnum
import cc.loac.data.requests.FileGroupUpdateRequest
import cc.loac.data.requests.FileMoveRequest
import cc.loac.data.responses.FileResponse
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.FileDao
import cc.loac.extensions.addRandomSuffix
import cc.loac.extensions.formatSlash
import cc.loac.extensions.jsonToClass
import cc.loac.extensions.toJSONString
import cc.loac.services.FileService
import cc.loac.utils.*
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
import java.io.InputStream
import java.util.*

/**
 * 文件服务接口实现类
 */
class FileServiceImpl : FileService {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val fileDao: FileDao by inject(FileDao::class.java)

    companion object {
        // 本地存储
        private val localStorage: FileOption = LocalFileStorageImpl()

        // 腾讯云对象存储
        private var tencentCOS: FileOption? = null

        // 腾讯云对象存储配置
        private var tencentConfig: TencentCOSConfig? = null
    }

    init {
        // 尝试初始化腾讯云对象存储
        ioScope.launch {
            initFileStorageMode(FileStorageModeEnum.TENCENT_COS)
        }
    }

    /**
     * 初始化文件存储方式（除了本地存储）
     * @param storageMode 对象存储方式
     * @return 已经初始化或初始化成功返回对象，初始化失败返回 null
     */
    private suspend fun initFileStorageMode(storageMode: FileStorageModeEnum): FileOption? {
        when (storageMode) {
            FileStorageModeEnum.TENCENT_COS -> {
                // 腾讯云对象存储已经初始化，直接返回对象
                if (tencentCOS != null) return tencentCOS
                // 腾讯云对象存储配置文件不存在，返回 null
                val configStr = fileDao.getFileStorageConfig(storageMode) ?: return null
                tencentConfig = configStr.jsonToClass()
                // 设置腾讯云对象存储
                tencentCOS = TencentCOSImpl.getInstance(tencentConfig)
                return tencentCOS
            }

            else -> {}
        }
        return null
    }

    /**
     * 设置腾讯云对象存储配置
     * @param config 腾讯云对象存储配置
     */
    override suspend fun setTencentCOS(config: TencentCOSConfig): Boolean {
        return fileDao.setFileStorageConfig(
            FileStorageModeEnum.TENCENT_COS,
            config.toJSONString()
        ).also { result ->
            if (result) {
                // 设置腾讯云对象存储成功，重新设置腾讯云对象存储变量
                tencentCOS = null
                initFileStorageMode(FileStorageModeEnum.TENCENT_COS)
            }
        }
    }

    /**
     * 删除腾讯云对象存储配置
     */
    override suspend fun deleteTencentCOS(): Boolean {
        // 先判断腾讯云对象存储下是否还有文件
        val fileCount = fileDao.getFileCount(FileStorageModeEnum.TENCENT_COS)
        if (fileCount > 0) throw MyException("腾讯云对象存储策略下还有 $fileCount 个文件，无法删除")
        return fileDao.deleteFileStorageConfig(FileStorageModeEnum.TENCENT_COS).also { result ->
            if (result) {
                // 删除腾讯云对象存储成功，清空腾讯云对象存储变量
                tencentCOS = null
                tencentConfig = null
            }
        }
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
            throw MyException("文件组名 [${fileGroup.displayName}] 已经存在")
        }

        if (getFileGroupByPath(fileGroup.storageMode, fileGroup.path) != null) {
            throw MyException("文件组路径 [${fileGroup.path}] 已经存在")
        }

        // 添加文件组
        return fileDao.addFileGroup(fileGroup)
    }

    /**
     * 删除文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun deleteFileGroup(fileGroupId: Long): Boolean {
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
        val oldFileGroup =
            getFileGroup(fileGroup.fileGroupId) ?: throw MyException("文件组 [${fileGroup.fileGroupId}]不存在")

        // 如果文件组名已经存在，并且不是自己的话，抛出异常
        val fg = getFileGroupByDisplayName(oldFileGroup.storageMode, fileGroup.displayName)
        if (fg != null && fg.fileGroupId != fileGroup.fileGroupId) {
            throw MyException("文件组名 [${fileGroup.displayName}}] 已经存在")
        }

        // 修改文件组
        return fileDao.updateFileGroup(fileGroup)
    }

    /**
     * 根据文件组 ID 获取文件组
     * @param fileGroupId 文件组 ID
     */
    override suspend fun getFileGroup(fileGroupId: Long): FileGroup? {
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

    /**
     * 上传文件
     * @param inputStream 文件二进制数据
     * @param fileName 文件名
     * @param storageMode 文件存储方式
     * @param fileGroupId 文件组 ID
     * @param fileLength 文件长度
     */
    override suspend fun uploadFile(
        inputStream: InputStream,
        fileName: String,
        storageMode: FileStorageModeEnum,
        fileGroupId: Long?,
        fileLength: Long?
    ): FileResponse? {
        var fileGroup: FileGroup? = null
        if (fileGroupId != null) {
            // 先尝试获取文件组
            fileGroup = getFileGroup(fileGroupId)
            if (fileGroup?.storageMode == null || fileGroup.storageMode != storageMode) {
                throw MyException("文件组 [$fileGroupId] 不存在")
            }
        }

        // 如果存储方式不为本地存储，就先查看对应的存储方式是否已经配置
        if (storageMode != FileStorageModeEnum.LOCAL) {
            // 先尝试初始化存储方式
            initFileStorageMode(FileStorageModeEnum.TENCENT_COS) ?: throw FileStorageNotConfiguredException(
                FileStorageModeEnum.TENCENT_COS
            )
        }


        // 查看当前文件名是否已经存在
        val file = fileDao.getFile(fileName, fileGroupId, storageMode)
        var actualFileName = fileName
        // 如果文件已经存在，给文件名加上 5 个随机数字或字母
        if (file != null) {
            actualFileName = actualFileName.addRandomSuffix()
        }

        val result = when (storageMode) {
            FileStorageModeEnum.LOCAL -> {
                // 本地存储
                localStorage.uploadFile(
                    inputStream = inputStream,
                    path = fileGroup?.path ?: "",
                    fileName = actualFileName
                )
            }

            FileStorageModeEnum.TENCENT_COS -> {
                // 腾讯云对象存储
                // 上传文件
                // 按官方文档说腾讯云上传的文件最好给定文件的大小，不然会腾讯云对象存储会计算流长度
                // 这会导致耗时操作，并且会占用不必要的内存
                // 但是，请求头 Content-Length 传过来 fileLength 长度似乎和腾讯云对象存储的算出的不一致，
                // 这会导致腾讯云对象存储报错，导致上传失败，这是待解决问题！
                tencentCOS!!.uploadFile(
                    inputStream = inputStream,
                    path = fileGroup?.path ?: "",
                    fileName = actualFileName
                )
            }
        }

        // 如果文件上传成功，就将新文件插入数据库
        if (result) {
            // 文件上传成功，将文件信息添加到数据库
            val newFileResult = fileDao.addFile(
                MFile(
                    fileId = -1,
                    fileGroupId = fileGroupId,
                    displayName = actualFileName,
                    size = fileLength ?: 1L,
                    storageMode = storageMode,
                    createTime = Date().time
                )
            )
            return FileResponse(
                fileId = newFileResult?.fileId ?: -1,
                fileGroupId = fileGroupId,
                fileGroupName = fileGroup?.displayName,
                displayName = actualFileName,
                url = if (storageMode == FileStorageModeEnum.LOCAL) {
                    ("/upload/" + (fileGroup?.path ?: "") + "/$actualFileName").formatSlash()
                } else {
                    tencentCOSUrl(tencentConfig!!, actualFileName, fileGroup?.displayName)
                },
                size = fileLength ?: 1L,
                storageMode = storageMode,
                createTime = newFileResult?.createTime ?: Date().time
            )

        } else {
            return null
        }
    }

    /**
     * 根据文件 ID 删除文件
     * @param ids 文件 ID 集合
     * @return 删除成功的文件 ID 集合
     */
    override suspend fun deleteFiles(ids: List<Long>): List<Long> {
        if (ids.isEmpty()) return emptyList()

        // 根据文件 ID 获取所有文件
        val files: List<FileWithGroup> = fileDao.getFileWithGroups(ids)
        // 将要删除的文件封装成文件索引数据类 [FileIndex]
        val fileIndexes = files.map {
            FileIndex(
                fileId = it.fileId,
                name = "${it.fileGroupPath ?: ""}/${it.fileName}",
                storageMode = it.storageMode
            )
        }
        // 删除文件，获取成功删除的文件索引数据类
        val deletedResult = deleteFilesByFineIndexes(fileIndexes)
        // 返回删除成功的文件 ID
        return if (deletedResult.size == ids.size) {
            // 成功删除的文件数量和要删除的文件数量相同，返回要删除的文件 ID 集合
            ids
        } else {
            // 返回删除成功的文件 ID
            fileIndexes.map { it.fileId!! }
        }
    }

    /**
     * 根据文件索引删除文件
     * @param fileIndexes 文件索引数组
     * @return 删除成功的文件索引数组
     */
    override suspend fun deleteFilesByFineIndexes(fileIndexes: List<FileIndex>): List<FileIndex> {
        if (fileIndexes.isEmpty()) return emptyList()
        // 将不同存储形式的文件分离
        val localFileIndexes = fileIndexes.filter { it.storageMode == FileStorageModeEnum.LOCAL }
        val tencentFileIndexes = fileIndexes.filter { it.storageMode == FileStorageModeEnum.TENCENT_COS }
        // 成功删除的文件索引集合
        val resultFileIndexes = LinkedList<FileIndex>()
        if (tencentFileIndexes.isNotEmpty()) {
            // 先尝试初始化存储方式
            initFileStorageMode(FileStorageModeEnum.TENCENT_COS) ?: throw FileStorageNotConfiguredException(
                FileStorageModeEnum.TENCENT_COS
            )

            // 删除腾讯云对象存储文件
            val tencentDeleteResult = tencentCOS!!.deleteFiles(tencentFileIndexes.map { it.name })
            if (tencentDeleteResult.size == tencentFileIndexes.size) {
                // 如果成功删除的文件数目与请求删除的文件数目相等，就将所有请求删除的文件都加入删除成功结果集
                resultFileIndexes.addAll(tencentFileIndexes)
            } else {
                // 如果成功删除的文件数目与请求删除的文件数目不相等，就只把成功删除的文件加入删除成功结果集
                tencentDeleteResult.forEach {
                    resultFileIndexes.add(tencentFileIndexes.find { fileIndex -> fileIndex.name == it }!!)
                }
            }
        }

        // 如果本地存储文件不为空，就删除本地文件
        if (localFileIndexes.isNotEmpty()) {
            val localDeleteResult = localStorage.deleteFiles(localFileIndexes.map { it.name })
            if (localDeleteResult.size == localFileIndexes.size) {
                // 如果成功删除的文件数目与请求删除的文件数目相等，就将所有请求删除的文件都加入删除成功结果集
                resultFileIndexes.addAll(localFileIndexes)
            } else {
                // 如果成功删除的文件数目与请求删除的文件数目不相等，就只把成功删除的文件加入删除成功结果集
                localDeleteResult.forEach {
                    resultFileIndexes.add(localFileIndexes.find { fileIndex -> fileIndex.name == it }!!)
                }
            }
        }

        if (resultFileIndexes.isNotEmpty()) {
            // 如果删除成功的文件不为空，就删除数据库中的文件记录
            deleteDatabaseFilesByFileIndexes(resultFileIndexes)
        }

        // 返回删除成功的结果集
        return resultFileIndexes
    }

    /**
     * 移动文件
     * @param fileMoveRequest 文件移动请求数据类
     * @return 移动成功旧文件的地址集合
     */
    override suspend fun moveFiles(fileMoveRequest: FileMoveRequest): List<String> {
        if (fileMoveRequest.fileIds.isEmpty()) return emptyList()
        // 先获取所有要移动的文件
        val files = fileDao.getFileWithGroups(fileMoveRequest.fileIds)
        if (files.isEmpty()) return emptyList()
        var newFileGroup: FileGroup? = null

        // 判断是否所有的文件都属于同一存储方式
        val firstFile = files.first()
        if (files.size > 1 && files.find { it.storageMode != firstFile.storageMode } != null) {
            // 待移动的文件不是同属于一个存储方式
            throw MyException("待移动的文件必须属于同一存储策略")
        }

        // 判断要移动进的新的文件组是否存在
        if (fileMoveRequest.newFileGroupId != null &&
            fileMoveRequest.newFileGroupId >= 0
        ) {
            newFileGroup = getFileGroup(fileMoveRequest.newFileGroupId)
            if (newFileGroup == null || newFileGroup.storageMode != firstFile.storageMode) {
                // 文件组为空，或者存储方式与待移动的文件不一致
                // 换句话说，如果当前文件已经在目标文件组中，就不进行操作
                throw MyException("文件组 [${fileMoveRequest.newFileGroupId}] 不存在")
            }
        }

        // 待移动的文件名（包括文件组路径）
        val waitForMoveFileNames = LinkedList<String>()
        files.forEach { fileWithGroup ->
            if (fileWithGroup.fileGroupId != newFileGroup?.fileGroupId) {
                // 如果要移动的文件，不在目标文件组中，就加入待移动的文件集合中
                waitForMoveFileNames.add(
                    (((fileWithGroup.fileGroupPath ?: "") + "/${fileWithGroup.fileName}").formatSlash())
                )
            }
        }

        // 成功移动的文件名
        val movedFileNames = mutableListOf<String>()
        when (firstFile.storageMode) {
            FileStorageModeEnum.LOCAL -> {
                // 本地文件移动
                localStorage.moveFile(waitForMoveFileNames, newFileGroup?.path ?: "").let {
                    // 将成功移动的文件名加入结果集合
                    movedFileNames.addAll(it)
                }
            }

            FileStorageModeEnum.TENCENT_COS -> {
                // 腾讯云对象存储文件移动
                // 先尝试初始化腾讯云对象存储方式
                // 先尝试初始化存储方式
                initFileStorageMode(FileStorageModeEnum.TENCENT_COS) ?: throw FileStorageNotConfiguredException(
                    FileStorageModeEnum.TENCENT_COS
                )
                tencentCOS!!.moveFile(waitForMoveFileNames, newFileGroup?.path ?: "").let {
                    // 将成功移动的文件名加入结果集合
                    movedFileNames.addAll(it)
                }
            }
        }

        // 修改成功移动的文件的文件组
        val newFiles = mutableListOf<MFile>()
        movedFileNames.forEach { fileName ->
            // 寻找成功删除的文件
            val file = files.find {
                ((it.fileGroupPath ?: "") + "/${it.fileName}") == fileName
            }

            if (file != null) {
                newFiles.add(
                    MFile(
                        fileId = file.fileId,
                        // 关键修改点
                        fileGroupId = newFileGroup?.fileGroupId,
                        displayName = file.fileName,
                        size = file.size,
                        storageMode = file.storageMode,
                        createTime = file.createTime
                    )
                )
            }
        }

        // 修改成功移动的文件的文件组
        fileDao.updateFiles(newFiles)
        return movedFileNames
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
    override suspend fun getFiles(
        page: Int,
        size: Int,
        sort: FileSort?,
        mode: FileStorageModeEnum?,
        groupId: Long?,
        key: String?
    ): Pager<FileResponse> {
        val fileResponses = LinkedList<FileResponse>()
        // 先分页获取文件和文件组分页数据
        val fileWithGroupPager = fileDao.getFileWithGroups(page, size, sort, mode, groupId, key)
        fileWithGroupPager.data.forEach {
            fileResponses.add(
                FileResponse(
                    fileId = it.fileId,
                    fileGroupId = it.fileGroupId,
                    fileGroupName = it.fileGroupName,
                    displayName = it.fileName,
                    url = when (it.storageMode) {
                        // 本地存储，返回相对地址
                        FileStorageModeEnum.LOCAL ->
                            "$URL_STORAGE_PATH/${it.fileGroupPath ?: ""}/${it.fileName}".formatSlash()
                        // 腾讯云对象存储，返回绝对地址
                        FileStorageModeEnum.TENCENT_COS -> {
                            // 先尝试初始化腾讯云对象存储
                            initFileStorageMode(FileStorageModeEnum.TENCENT_COS)
                                ?: throw FileStorageNotConfiguredException(FileStorageModeEnum.TENCENT_COS)
                            tencentCOSUrl(tencentConfig!!, it.fileName, it.fileGroupPath)
                        }

                    },
                    size = it.size,
                    storageMode = it.storageMode,
                    createTime = it.createTime
                )
            )
        }

        return Pager(
            page = fileWithGroupPager.page,
            size = fileWithGroupPager.size,
            data = fileResponses,
            totalData = fileWithGroupPager.totalData,
            totalPages = fileWithGroupPager.totalPages
        )
    }

    /**
     * 根据文件索引数据类删除数据库中总的文件记录
     * @param fileIndexes 文件索引数据类集合
     */
    private suspend fun deleteDatabaseFilesByFileIndexes(fileIndexes: List<FileIndex>): Boolean {
        // 因为 FileIndex 的 name 文件名属性，是包含了文件组的名字，如：/img/1.jpg
        // 但是文件表的 displayName 文件名字段只会存储 1.jpg，所以需要取出真实文件名
        val localFiles = fileIndexes.filter { it.storageMode == FileStorageModeEnum.LOCAL }.map {
            it.name.substringAfterLast("/")
        }
        val tencentFiles = fileIndexes.filter { it.storageMode == FileStorageModeEnum.TENCENT_COS }.map {
            it.name.substringAfterLast("/")
        }
        if (localFiles.isNotEmpty()) {
            fileDao.deleteFile(localFiles, FileStorageModeEnum.LOCAL)
        }
        if (tencentFiles.isNotEmpty()) {
            fileDao.deleteFile(tencentFiles, FileStorageModeEnum.TENCENT_COS)
        }
        return true
    }
}