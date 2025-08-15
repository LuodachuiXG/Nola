package cc.loac.services

import cc.loac.data.models.Category
import cc.loac.data.models.User
import cc.loac.data.responses.Pager

/**
 * 分类服务接口
 */
interface CategoryService {

    /**
     * 添加分类
     * @param category 分类数据类
     */
    suspend fun addCategory(
        category: Category
    ): Category?

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    suspend fun deleteCategories(ids: List<Long>): Boolean

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
     * 分页获取所有分类
     * @param page 当前页数
     * @param size 每页条数
     */
    suspend fun categories(page: Int, size: Int): Pager<Category>

    /**
     * 获取文章数量最多的 6 个分类
     */
    suspend fun topCategories(): List<Category>

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    suspend fun category(id: Long): Category?

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

    /**
     * 判断分类别名是否已经存在，并且不是当前分类
     */
    suspend fun isSlugExist(category: Category): Boolean

    /**
     * 分类数量
     */
    suspend fun categoryCount(): Long
}