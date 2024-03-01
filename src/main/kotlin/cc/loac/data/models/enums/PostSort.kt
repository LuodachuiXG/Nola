package cc.loac.data.models.enums

/**
 * 文章排序枚举类
 */
enum class PostSort {
    /** 创建时间降序 **/
    CREATE_DESC,
    /** 创建时间升序 **/
    CREATE_ASC,
    /** 最后修改时间降序 **/
    MODIFY_DESC,
    /** 最后修改时间升序 **/
    MODIFY_ASC,
    /** 访问量降序 **/
    VISIT_DESC,
    /** 访问量升序 **/
    VISIT_ASC
}