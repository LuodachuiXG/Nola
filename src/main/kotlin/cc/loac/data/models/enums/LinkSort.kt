package cc.loac.data.models.enums

/**
 * 友情链接排序枚举类
 */
enum class LinkSort {
    /** 优先级降序排序 **/
    PRIORITY_DESC,
    /** 优先级升序排序 **/
    PRIORITY_ASC,
    /** 创建时间降序排序 **/
    CREATE_TIME_DESC,
    /** 创建时间升序排序 **/
    CREATE_TIME_ASC,
    /** 修改时间降序排序 **/
    MODIFY_TIME_DESC,
    /** 修改时间升序排序 **/
    MODIFY_TIME_ASC
}