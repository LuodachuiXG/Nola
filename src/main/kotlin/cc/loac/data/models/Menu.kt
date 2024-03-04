package cc.loac.data.models

/**
 * 菜单数据类
 */
data class Menu(
    /** 菜单 ID **/
    val menuId: Int,
    /** 是否是主菜单 **/
    val isMain: Boolean,
    /** 菜单名 **/
    val displayName: String,
    /** 创建时间 **/
    val createTime: Long,
    /** 最后修改时间 **/
    val lastModifyTime: Long?
)
