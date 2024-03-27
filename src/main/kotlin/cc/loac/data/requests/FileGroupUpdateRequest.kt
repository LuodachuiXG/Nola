package cc.loac.data.requests


/**
 * 文件组修改请求数据类
 * @param fileGroupId 文件组 ID
 * @param displayName 文件组名
 * @param path 文件组路径
 */
data class FileGroupUpdateRequest(
    val fileGroupId: Int,
    val displayName: String,
    val path: String
)