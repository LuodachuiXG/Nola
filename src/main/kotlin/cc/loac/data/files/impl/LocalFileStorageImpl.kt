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
     * 批量删除文件
     * @param fileNames 文件名列表
     * @return 成功删除的文件名列表
     */
    override fun deleteFiles(fileNames: List<String>): List<String> {
        val result = mutableListOf<String>()
        fileNames.forEach { fileName ->
            val file = File("$localStoragePath/$fileName")
            if (file.delete()) result.add(fileName)
        }
        return result
    }

    /**
     * 移动文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更新成功
     */
    override fun moveFile(oldName: String, newName: String): Boolean {
        val oldFile = File("$localStoragePath/$oldName")
        val newFile = File("$localStoragePath/$newName")
        return oldFile.renameTo(newFile)
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