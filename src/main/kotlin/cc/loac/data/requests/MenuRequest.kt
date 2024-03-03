package cc.loac.data.requests

/**
 * 菜单请求数据类
 */
data class MenuRequest(
    /** 菜单 ID **/
    val menuId: Int,
    /** 菜单名 **/
    val displayName: String,
    /** 是否是主菜单 **/
    val isMain: Boolean
)
