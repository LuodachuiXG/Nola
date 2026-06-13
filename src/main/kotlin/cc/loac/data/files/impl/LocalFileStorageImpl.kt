package cc.loac.data.files.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.files.FileOption
import java.io.File
import java.io.InputStream

// URL 存储路径
const val URL_STORAGE_PATH = "/upload"

// 本地存储路径
const val LOCAL_STORAGE_PATH = ".nola/upload"

/**
 * 本地存储实现类
 */
class LocalFileStorageImpl : FileOption {

    // 本地存储根目录的规范路径，用于路径遍历校验
    private val storageRoot: File = File(LOCAL_STORAGE_PATH).canonicalFile

    init {
        // 检查文件夹是否存在
        if (!storageRoot.exists()) {
            // 创建目录
            storageRoot.mkdirs()
        }
    }

    /**
     * 校验文件是否位于本地存储根目录内，防止路径遍历
     */
    private fun File.isInsideStorage(): Boolean {
        val root = storageRoot.canonicalPath
        val path = canonicalPath
        return path == root || path.startsWith("$root${File.separator}")
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
        val filePath = "$LOCAL_STORAGE_PATH/$path"
        return try {
            // 判断文件路径（文件夹是否存在）
            val newPath = File(filePath)
            if (!newPath.isInsideStorage()) {
                throw MyException("文件路径非法")
            }
            if (!newPath.exists()) newPath.mkdirs()

            // 创建文件
            val newFile = File("$filePath/$fileName")
            if (!newFile.isInsideStorage()) {
                throw MyException("文件路径非法")
            }
            newFile.outputStream().use {
                inputStream.copyTo(it)
            }
            true
        } catch (e: Exception) {
            throw MyException(e.message ?: "本地文件上传失败", e)
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
            val file = File("$LOCAL_STORAGE_PATH/$fileName")
            if (file.isInsideStorage() && file.delete()) result.add(fileName)
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
        val newGroupDir = File("$LOCAL_STORAGE_PATH/$newGroupName")
        if (!newGroupDir.isInsideStorage()) {
            return result
        }
        if (!newGroupDir.exists()) newGroupDir.mkdirs()
        oldFileNames.forEach { oldFileName ->
            val oldFile = File("$LOCAL_STORAGE_PATH/$oldFileName")
            if (oldFile.isInsideStorage() && oldFile.exists()) {
                val newFile = File(
                    "$LOCAL_STORAGE_PATH/$newGroupName/" +
                            oldFileName.substringAfterLast("/")
                )
                if (newFile.isInsideStorage() && oldFile.renameTo(newFile)) {
                    result.add(oldFileName)
                }

                // 如果被移动的文件的父文件夹下没有文件了，就删除该文件夹
                val parentPath = oldFileName.substringBeforeLast("/")
                if (parentPath.isNotBlank() && parentPath != oldFileName) {
                    val parentDir = File("$LOCAL_STORAGE_PATH/$parentPath")
                    if (parentDir.isInsideStorage() && parentDir.isDirectory && parentDir.listFiles()?.isEmpty() == true) {
                        parentDir.delete()
                    }
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
        val file = File("$LOCAL_STORAGE_PATH/$fileName")
        return file.isInsideStorage() && file.exists()
    }
}