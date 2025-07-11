package cc.loac.data.files

import java.io.InputStream

/**
 * 文件操作接口
 */
interface FileOption {
    /**
     * 上传文件
     * @param inputStream 文件输入流
     * @param path 文件路径
     * @param fileName 文件名
     * @return 文件是否上传成功
     */
    fun uploadFile(
        inputStream: InputStream,
        path: String,
        fileName: String
    ): Boolean

    /**
     * 批量删除文件
     * @param fileNames 文件名列表
     * @return 成功删除的文件名列表
     */
    fun deleteFiles(fileNames: List<String>): List<String>

    /**
     * 移动文件
     * @param oldFileNames 旧文件名集合
     * @param newGroupName 要移动到的新的文件组（文件夹）名
     * @return 成功移动的文件的旧文件名（包括文件夹名）
     */
    fun moveFile(oldFileNames: List<String>, newGroupName: String): List<String>


    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 是否存在
     */
    fun isExist(fileName: String): Boolean
}