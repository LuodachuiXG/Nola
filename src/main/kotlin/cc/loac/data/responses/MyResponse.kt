package cc.loac.data.responses

import kotlinx.serialization.Serializable

/**
 * 响应类数据类
 * @param code 响应代码
 * @param errMsg 错误信息
 * @param data 响应数据
 */
@Serializable
data class MyResponse<T> (
    val code: Int,
    val errMsg: String?,
    val data: T?
)