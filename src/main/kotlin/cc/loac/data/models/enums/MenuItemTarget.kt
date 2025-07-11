package cc.loac.data.models.enums

/**
 * 菜单项打开方式枚举类
 */
enum class MenuItemTarget {
    /** 新窗口打开（默认） */
    BLANK,
    /** 当前窗口打开 */
    SELF,
    /** 父窗口打开 */
    PARENT,
    /** 顶级窗口打开 */
    TOP
}