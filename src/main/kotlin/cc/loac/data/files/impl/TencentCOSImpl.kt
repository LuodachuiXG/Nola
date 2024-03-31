package cc.loac.data.files.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.files.FileOption
import cc.loac.data.files.config.TencentCOSConfig
import cc.loac.utils.error
import cc.loac.utils.formatSlash
import cc.loac.utils.replaceDoubleSlash
import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.http.HttpProtocol
import com.qcloud.cos.model.CopyObjectRequest
import com.qcloud.cos.model.DeleteObjectsRequest
import com.qcloud.cos.model.DeleteObjectsRequest.KeyVersion
import com.qcloud.cos.model.ObjectMetadata
import com.qcloud.cos.model.PutObjectRequest
import com.qcloud.cos.model.RenameRequest
import com.qcloud.cos.region.Region
import java.io.InputStream

/**
 * 腾讯云对象存储实现类
 */
class TencentCOSImpl private constructor() : FileOption {
    companion object {
        private var _cosClient: COSClient? = null
        private var _config: TencentCOSConfig? = null

        fun getInstance(config: TencentCOSConfig?): TencentCOSImpl {
            this._config = config
            if (_cosClient == null && config == null)
                throw IllegalArgumentException("腾讯云对象存储还未初始化，config 不能为 null")
            if (_cosClient != null && config == null) return TencentCOSImpl()
            if (config != null) {
                val cred = BasicCOSCredentials(config.secretId, config.secretKey)
                val region = Region(config.region)
                val clientConfig = ClientConfig(region)
                clientConfig.httpProtocol = if (config.https)
                    HttpProtocol.https else HttpProtocol.http
                _cosClient = COSClient(cred, clientConfig)
            }
            return TencentCOSImpl()
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
        val objectMetadata = ObjectMetadata()
        val key = "${_config!!.path ?: ""}/$path/${fileName}".formatSlash()
        val putObjectRequest =
            PutObjectRequest(_config!!.bucket, key, inputStream, objectMetadata)
        return try {
            _cosClient!!.putObject(putObjectRequest)
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
        val deleteObjectsRequest = DeleteObjectsRequest(_config!!.bucket)
        val keyList = fileNames.map { fileName ->
            KeyVersion("${_config!!.path ?: ""}/${fileName}".formatSlash())
        }
        deleteObjectsRequest.keys = keyList
        return try {
            val deleteObjectsResult = _cosClient!!.deleteObjects(deleteObjectsRequest)
            deleteObjectsResult.deletedObjects.map { it.key }
        } catch (e: Exception) {
            throw MyException(e.message ?: "未知错误")
        }
    }

    /**
     * 复制文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否复制成功
     */
    private fun copyFile(oldName: String, newName: String): Boolean {
        val mOldName = "${_config!!.path ?: ""}/${oldName}".formatSlash()
        val mNewName = "${_config!!.path ?: ""}/${newName}".formatSlash()
        val copyRequest = CopyObjectRequest(
            Region(_config!!.region), _config!!.bucket, mOldName, _config!!.bucket, mNewName
        )
        return try {
            _cosClient!!.copyObject(copyRequest)
            true
        } catch (e: Exception) {
            throw MyException(e.message ?: "未知错误")
        }
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
        // 先复制文件，成功后再删除源文件
        // 腾讯云对象存储目前好像只能一个一个文件复制
        oldFileNames.forEach { oldName ->
            val newName = newGroupName + "/${oldName.substringAfterLast("/")}"
            if (copyFile(oldName, newName)) {
                // 复制成功，再将旧文件名加入结果集合
                result.add(oldName)
            }
        }

        // 全部复制完成，删除旧文件
        deleteFiles(result)
        return result
    }

    /**
     * 判断文件是否存在
     * @param fileName 文件名
     * @return 是否存在
     */
    override fun isExist(fileName: String): Boolean {
        try {
            return _cosClient!!.doesObjectExist(
                _config!!.bucket,
                "${_config!!.path ?: ""}/${fileName}".formatSlash()
            )
        } catch (e: Exception) {
            throw MyException(e.message ?: "未知错误")
        }
    }
}