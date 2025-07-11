package cc.loac.data.requests

/**
 * 文件移动请求数据类
 * @param fileIds 要移动的文件 ID 集合
 * @param newFileGroupId 新的文件组 ID
 */
data class FileMoveRequest(
    val fileIds: List<Long>,
    val newFileGroupId: Long?
)
