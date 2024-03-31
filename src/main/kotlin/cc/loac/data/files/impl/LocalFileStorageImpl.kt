package cc.loac.data.files.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.files.FileOption
import cc.loac.utils.error
import cc.loac.utils.formatSlash
import cc.loac.utils.replaceDoubleSlash
import java.io.File
import java.io.InputStream
import java.util.LinkedList

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
     * @param inputStream 文件输入流
     * @param path 文件路径
     * @param fileName 文件名
     * @return 文件是否上传成功
     */
    override fun uploadFile(
        inputStream: InputStream,
        path: String,
        fileName: String
    ): Boolean {
        val filePath = "$localStoragePath/$path"
        return try {
            // 判断文件路径（文件夹是否存在）
            val newPath = File(filePath)
            if (!newPath.exists()) newPath.mkdirs()

            // 创建文件
            val newFile = File("$filePath/$fileName")
            newFile.outputStream().use {
                inputStream.copyTo(it)
            }
            true
        } catch (e: Exception) {
            throw MyException(e.message ?: "未知错误")
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
     * @param oldFileNames 旧文件名集合
     * @param newGroupName 要移动到的新的文件组（文件夹）名
     * @return 成功移动的文件的旧文件名（包括文件夹名）
     */
    override fun moveFile(oldFileNames: List<String>, newGroupName: String): List<String> {
        // 成功移动的文件的旧文件名
        val result = mutableListOf<String>()
        val newGroupDir = File("$localStoragePath/$newGroupName")
        if (!newGroupDir.exists()) newGroupDir.mkdirs()
        oldFileNames.forEach { oldFileName ->
            val oldFile = File("$localStoragePath/$oldFileName")
            if (oldFile.exists()) {
                val newFile = File(
                    "$localStoragePath/$newGroupName/" +
                            oldFileName.substringAfterLast("/")
                )
                if (oldFile.renameTo(newFile)) {
                    result.add(oldFileName)
                }
            }
        }
        return result
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