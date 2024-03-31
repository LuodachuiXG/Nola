package cc.loac.data.requests


/**
 * 文件组修改请求数据类
 * 文件组的存储方式和文件组地址不能修改
 * @param fileGroupId 文件组 ID
 * @param displayName 文件组名
 */
data class FileGroupUpdateRequest(
    val fileGroupId: Int,
    val displayName: String
)