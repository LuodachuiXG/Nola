package cc.loac.data.responses

/**
 * 分页数据类
 * @param page 当前页
 * @param size 每页条数
 * @param data 数据集合
 * @param totalData 总条数
 * @param totalPages 总页数
 */
data class Pager<T>(
    val page: Int,
    val size: Int,
    val data: List<T>,
    val totalData: Long,
    val totalPages: Long
)