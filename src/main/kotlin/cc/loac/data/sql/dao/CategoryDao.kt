package cc.loac.data.sql.dao

import cc.loac.data.models.Category

/**
 * 分类表操作接口
 */
interface CategoryDao {
    /**
     * 添加分类
     * @param category 分类数据类
     */
    suspend fun addCategory(category: Category): Category?

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    suspend fun deleteCategories(ids: List<Int>): Boolean

    /**
     * 根据别名删除分类
     * @param slugs 分类别名集合
     */
    suspend fun deleteCategoriesBySlugs(slugs: List<String>): Boolean

    /**
     * 修改分类
     * @param category 分类数据类
     */
    suspend fun updateCategory(category: Category): Boolean

    /**
     * 获取所有分类
     */
    suspend fun categories(): List<Category>

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    suspend fun category(id: Int): Category?

    /**
     * 根据分类名称获取分类
     * @param displayName 分类名称
     */
    suspend fun category(displayName: String): Category?

    /**
     * 根据分类别名获取分类
     * @param slug 分类别名
     */
    suspend fun categoryBySlug(slug: String): Category?
}