package cc.loac.data.files.impl

import cc.loac.data.files.FileOption
import java.io.File

/**
 * 本地存储实现类
 */
class LocalFileStorageImpl : FileOption {

    // 本地存储路径
    private val localStoragePath = ".nola/upload"

    init {
        // 检查文件夹是否存在
        val dir = File(localStoragePath)
        if (!dir.exists()) {
            // 创建目录
            dir.mkdirs()
        }
    }

    /**
     * 上传文件
     * @param file 文件二进制数据
     * @param fileName 文件名
     * @return 是否上传成功
     */
    override fun uploadFile(file: ByteArray, fileName: String): Boolean {
        val filePath = "$localStoragePath/$fileName"
        return try {
            // 创建文件
            val newFile = File("$filePath/$fileName")
            // 写入文件
            newFile.writeBytes(file)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 删除文件
     * @param fileName 文件名
     * @return 是否删除成功
     */
    override fun deleteFile(fileName: String): Boolean {
        val file = File("$localStoragePath/$fileName")
        return file.delete()
    }

    /**
     * 更新文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更新成功
     */
    override fun updateFile(oldName: String, newName: String): Boolean {
        val oldFile = File("$localStoragePath/$oldName")
        if (!oldFile.exists()) return false
        val newFile = File("$localStoragePath/$newName")
        return oldFile.renameTo(newFile)
    }

    /**
     * 获取文件
     * @param fileName 文件名
     * @return 文件二进制数据
     */
    override fun getFile(fileName: String): ByteArray? {
        val file = File("$localStoragePath/$fileName")
        return if (file.exists()) {
            file.readBytes()
        } else {
            null
        }
    }

    /**
     * 获取所有文件
     * @return 文件名列表
     */
    override fun files(): List<String> {
        val file = File(localStoragePath)
        // 返回文件名列表
        return file.list()?.map { "/upload/$it" } ?: emptyList()
    }

    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 是否存在
     */
    override fun isExist(fileName: String): Boolean {
        val file = File("$localStoragePath/$fileName")
        return file.exists()
    }
}