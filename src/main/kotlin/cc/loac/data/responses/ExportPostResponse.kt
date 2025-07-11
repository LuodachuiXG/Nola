package cc.loac.data.responses


/**
 * 导出文章响应结果数据类
 * @param path 导出文件路径
 * @param count 总数量
 * @param successCount 成功数量
 * @param failCount 失败数量
 * @param failResult 失败信息
 */
data class ExportPostResponse(
    val path: String,
    val count: Int,
    val successCount: Int,
    val failCount: Int,
    val failResult: List<String>
)
