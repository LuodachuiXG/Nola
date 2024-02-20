package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Category
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Categories
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 分类表操作接口实现类
 */
class CategoryDaoImpl : CategoryDao {

    /**
     * 将数据库检索结果转为 [Category] 分类数据类
     */
    private fun resultRowToCategory(row: ResultRow) = Category(
        categoryId = row[Categories.categoryId],
        displayName = row[Categories.displayName],
        slug = row[Categories.slug],
        cover = row[Categories.cover],
        unifiedCover = row[Categories.unifiedCover]
    )

    /**
     * 添加分类
     * @param category 分类数据类
     */
    override suspend fun addCategory(category: Category): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[displayName] = category.displayName
            it[slug] = category.slug
            it[cover] = category.cover
            it[unifiedCover] = category.unifiedCover
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    override suspend fun deleteCategories(ids: List<Int>): Boolean = dbQuery {
        Categories.deleteWhere {
            categoryId inList ids
        } > 0
    }

    /**
     * 根据别名删除分类
     * @param slugs 分类别名集合
     */
    override suspend fun deleteCategoriesBySlugs(slugs: List<String>): Boolean = dbQuery {
        Categories.deleteWhere {
            slug inList slugs
        } > 0
    }

    /**
     * 修改分类
     * @param category 分类数据类
     */
    override suspend fun updateCategory(category: Category): Boolean = dbQuery {
        Categories.update({
            Categories.categoryId eq category.categoryId
        }) {
            it[displayName] = category.displayName
            it[slug] = category.slug
            it[cover] = category.cover
            it[unifiedCover] = category.unifiedCover
        } > 0
    }

    /**
     * 获取所有分类
     */
    override suspend fun categories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    /**
     * 分页获取所有分类
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun categories(page: Int, size: Int): Pager<Category> {
        return Categories.startPage(page, size, ::resultRowToCategory) {
            selectAll()
        }
    }

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    override suspend fun category(id: Int): Category? = dbQuery {
        Categories
            .selectAll().where { Categories.categoryId eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 根据分类名称获取分类
     * @param displayName 分类名称
     */
    override suspend fun category(displayName: String): Category? = dbQuery {
        Categories
            .selectAll().where { Categories.displayName eq displayName }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 根据分类别名获取分类
     * @param slug 分类别名
     */
    override suspend fun categoryBySlug(slug: String): Category? = dbQuery {
        Categories
            .selectAll().where { Categories.slug eq slug }
            .map(::resultRowToCategory)
            .singleOrNull()
    }
}