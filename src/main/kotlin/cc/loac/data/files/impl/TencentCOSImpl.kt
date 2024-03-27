package cc.loac.data.files.impl

import cc.loac.data.files.FileOption
import cc.loac.data.files.config.TencentCOSConfig
import com.qcloud.cos.COSClient
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.auth.BasicCOSCredentials
import com.qcloud.cos.http.HttpProtocol
import com.qcloud.cos.model.CopyObjectRequest
import com.qcloud.cos.model.DeleteObjectsRequest
import com.qcloud.cos.model.DeleteObjectsRequest.KeyVersion
import com.qcloud.cos.model.ObjectMetadata
import com.qcloud.cos.model.PutObjectRequest
import com.qcloud.cos.region.Region

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
     * @param file 文件二进制数据
     * @param fileName 文件名
     * @return 是否上传成功
     */
    override fun uploadFile(file: ByteArray, fileName: String): Boolean {
        val inputStream = file.inputStream()
        val objectMetadata = ObjectMetadata()
        val key = "${_config!!.path ?: ""}/${fileName.removeFirstSlash()}"
        val putObjectRequest =
            PutObjectRequest(_config!!.bucket, key, inputStream, objectMetadata)
        try {
            _cosClient!!.putObject(putObjectRequest)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
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
            KeyVersion("${_config!!.path ?: ""}/${fileName.removeFirstSlash()}")
        }
        deleteObjectsRequest.keys = keyList
        return try {
            val deleteObjectsResult = _cosClient!!.deleteObjects(deleteObjectsRequest)
            deleteObjectsResult.deletedObjects.map { it.key }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * 复制文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否复制成功
     */
    private fun copyFile(oldName: String, newName: String): Boolean {
        val mOldName = "${_config!!.path ?: ""}/${oldName.removeFirstSlash()}"
        val mNewName = "${_config!!.path ?: ""}/${newName.removeFirstSlash()}"
        val copyRequest = CopyObjectRequest(
            Region(_config!!.region), _config!!.bucket,
            mOldName, _config!!.bucket, mNewName
        )
        return try {
            _cosClient!!.copyObject(copyRequest)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 移动文件
     * @param oldName 旧文件名
     * @param newName 新文件名
     * @return 是否更新成功
     */
    override fun moveFile(oldName: String, newName: String): Boolean {
        // 先复制文件，成功后再删除源文件
        try {
            return if (copyFile(oldName, newName)) {
                // 复制成功，删除源文件
                deleteFiles(listOf(oldName)).isNotEmpty()
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
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
                "${_config!!.path ?: ""}/${fileName.removeFirstSlash()}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * String 扩展函数
     * 如果第一个字符是 / 就删除，否则不变
     */
    private fun String.removeFirstSlash(): String {
        return if (first() == '/' || first() == '\\') substring(1) else this
    }
}