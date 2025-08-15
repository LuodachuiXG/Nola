package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Category
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.services.CategoryService
import org.koin.java.KoinJavaComponent.inject

/**
 * 分类服务接口实现类
 */
class CategoryServiceImpl : CategoryService {

    private val categoryDao: CategoryDao by inject(CategoryDao::class.java)

    /**
     * 添加分类
     * @param category 分类数据类
     */
    override suspend fun addCategory(category: Category): Category? {
        // 先判断分类别名是否已经存在 .
        if (isSlugExist(category)) {
            throw MyException("分类别名 [${category.slug}] 已经存在")
        }
        return categoryDao.addCategory(category)
    }

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    override suspend fun deleteCategories(ids: List<Long>): Boolean {
        return categoryDao.deleteCategories(ids)
    }

    /**
     * 根据别名删除分类
     * @param slugs 分类别名集合
     */
    override suspend fun deleteCategoriesBySlugs(slugs: List<String>): Boolean {
        return categoryDao.deleteCategoriesBySlugs(slugs)
    }

    /**
     * 修改分类
     * @param category 分类数据类
     */
    override suspend fun updateCategory(category: Category): Boolean {
        // 先判断分类别名是否已经存在，并且不是当前分类
        if (isSlugExist(category)) {
            throw MyException("分类别名 [${category.slug}] 已经存在")
        }
        return categoryDao.updateCategory(category)
    }

    /**
     * 获取所有分类
     */
    override suspend fun categories(): List<Category> {
        return categoryDao.categories()
    }

    /**
     * 分页获取所有分类
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun categories(page: Int, size: Int): Pager<Category> {
        if (page == 0) {
            // 获取所有分类
            val categories = categories()
            return Pager(0, 0, categories, categories.size.toLong(), 1)
        }
        return categoryDao.categories(page, size)
    }

    /**
     * 获取文章数量最多的 6 个分类
     */
    override suspend fun topCategories(): List<Category> {
        return categoryDao.topCategories()
    }

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    override suspend fun category(id: Long): Category? {
        return categoryDao.category(id)
    }

    /**
     * 根据分类名称获取分类
     * @param displayName 分类名称
     */
    override suspend fun category(displayName: String): Category? {
        return categoryDao.category(displayName)
    }

    /**
     * 根据分类别名获取分类
     * @param slug 分类别名
     */
    override suspend fun categoryBySlug(slug: String): Category? {
        return categoryDao.categoryBySlug(slug)
    }

    /**
     * 判断分类别名是否已经存在，并且不是当前分类
     */
    override suspend fun isSlugExist(category: Category): Boolean {
        val c = categoryBySlug(category.slug)
        return c != null && c.categoryId != category.categoryId
    }

    /**
     * 分类数量
     */
    override suspend fun categoryCount(): Long {
        return categoryDao.categoryCount()
    }
}