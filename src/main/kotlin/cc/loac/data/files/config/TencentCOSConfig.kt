package cc.loac.data.files.config


/**
 * 腾讯云对象存储存储配置类
 * @param secretId 密钥 ID
 * @param secretKey 密钥 KEY
 * @param region 存储区域
 * @param bucket 存储桶
 * @param path 存储路径
 * @param https 是否使用 HTTPS
 */
data class TencentCOSConfig(
    val secretId: String,
    val secretKey: String,
    val region: String,
    val bucket: String,
    val path: String?,
    val https: Boolean
)