package cc.loac.data.responses

/**
 * 分页数据类
 */
data class Pager<T>(
    /** 当前页 **/
    val page: Int,
    /** 每页条数 **/
    val size: Int,
    /** 数据集合 **/
    val data: List<T>,
    /** 总条数 **/
    val totalData: Long,
    /** 总页数 **/
    val totalPages: Long
)