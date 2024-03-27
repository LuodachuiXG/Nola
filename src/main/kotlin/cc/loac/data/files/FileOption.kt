package cc.loac.data.files

/**
 * 文件操作接口
 */
interface FileOption {
    /**
     * 上传文件
     * @param file 文件二进制数据
     * @param fileName 文件名
     * @return 是否上传成功
     */
    fun uploadFile(file: ByteArray, fileName: String): Boolean

    /**
     * 批量删除文件
     * @param fileNames 文件名列表
     * @return 成功删除的文件名列表
     */
    fun deleteFiles(fileNames: List<String>): List<String>

    /**
     * 移动文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更新成功
     */
    fun moveFile(oldName: String, newName: String): Boolean


    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 是否存在
     */
    fun isExist(fileName: String): Boolean
}