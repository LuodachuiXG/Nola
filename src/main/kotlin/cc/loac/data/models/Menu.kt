package cc.loac.data.models

/**
 * 菜单数据类
 * @param menuId 菜单 ID
 * @param isMain 是否是主菜单
 * @param displayName 菜单名
 * @param createTime 创建时间
 * @param lastModifyTime 最后修改时间
 */
data class Menu(
    val menuId: Long,
    val isMain: Boolean,
    val displayName: String,
    val createTime: Long,
    val lastModifyTime: Long?
)
