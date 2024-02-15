package cc.loac.services.impl

import cc.loac.data.models.Category
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.services.CategoryService
import org.koin.java.KoinJavaComponent.inject

class CategoryServiceImpl : CategoryService {

    private val categoryDao: CategoryDao by inject(CategoryDao::class.java)

    /**
     * 添加分类
     * @param category 分类数据类
     */
    override suspend fun addCategory(category: Category): Category? {
        return categoryDao.addCategory(category)
    }

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    override suspend fun deleteCategories(ids: List<Int>): Boolean {
        return categoryDao.deleteCategories(ids)
    }

    /**
     * 修改分类
     * @param category 分类数据类
     */
    override suspend fun updateCategory(category: Category): Boolean {
        return categoryDao.updateCategory(category)
    }

    /**
     * 获取所有分类
     */
    override suspend fun categories(): List<Category> {
        return categoryDao.categories()
    }

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    override suspend fun category(id: Int): Category? {
        return categoryDao.category(id)
    }

    /**
     * 根据分类名称获取分类
     * @param displayName 分类名称
     */
    override suspend fun category(displayName: String): Category? {
        return categoryDao.category(displayName)
    }
}