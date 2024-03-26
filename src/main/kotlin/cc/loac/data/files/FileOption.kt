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
     * 删除文件
     * @param fileName 文件名
     * @return 是否删除成功
     */
    fun deleteFile(fileName: String): Boolean

    /**
     * 更新文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更新成功
     */
    fun updateFile(oldName: String, newName: String): Boolean

    /**
     * 获取文件
     * @param fileName 文件名
     * @return 文件二进制数据
     */
    fun getFile(fileName: String): ByteArray?

    /**
     * 获取所有文件
     * @return 文件名列表
     */
    fun files(): List<String>

    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 是否存在
     */
    fun isExist(fileName: String): Boolean
}