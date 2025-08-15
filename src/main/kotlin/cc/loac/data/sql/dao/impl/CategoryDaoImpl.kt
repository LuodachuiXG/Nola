package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Category
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Categories
import cc.loac.data.sql.tables.PostCategories
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

/**
 * 分类表操作接口实现类
 */
class CategoryDaoImpl : CategoryDao {

    /**
     * 将数据库检索结果转为 [Category] 分类数据类
     * @param includePostCount 是否包含文章数量
     */
    private fun resultRowToCategory(row: ResultRow, includePostCount: Boolean = true) = Category(
        categoryId = row[Categories.categoryId],
        displayName = row[Categories.displayName],
        slug = row[Categories.slug],
        cover = row[Categories.cover],
        unifiedCover = row[Categories.unifiedCover],
        postCount = if (includePostCount) row[PostCategories.categoryId.count()] else 0
    )

    /**
     * 添加分类
     * @param category 分类数据类
     */
    override suspend fun addCategory(category: Category): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[displayName] = category.displayName
            it[slug] = category.slug
            it[cover] = if (category.cover.isNullOrBlank()) null else category.cover
            it[unifiedCover] = category.unifiedCover
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRow ->
            resultRowToCategory(resultRow, false)
        }
    }

    /**
     * 删除分类
     * @param ids 分类 ID 集合
     */
    override suspend fun deleteCategories(ids: List<Long>): Boolean = dbQuery {
        // 先删除与分类对应的文章关联
        PostCategories.deleteWhere {
            categoryId inList ids
        }

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
            it[cover] = if (category.cover.isNullOrBlank()) null else category.cover
            it[unifiedCover] = category.unifiedCover
        } > 0
    }

    /**
     * 获取所有分类
     */
    override suspend fun categories(): List<Category> = dbQuery {
        sqlSelectCategory().map(::resultRowToCategory)
    }

    /**
     * 分页获取所有分类
     * @param page 当前页数
     * @param size 每页条数
     */
    override suspend fun categories(page: Int, size: Int): Pager<Category> {
        return Categories.startPage(page, size, ::resultRowToCategory) {
            sqlSelectCategory()
        }
    }

    /**
     * 获取文章数量最多的 6 个分类
     */
    override suspend fun topCategories(): List<Category> = dbQuery {
        sqlSelectCategory()
            .orderBy(PostCategories.categoryId.count(), SortOrder.DESC)
            .limit(6)
            .map(::resultRowToCategory)
    }

    /**
     * 根据文章 ID 获取文章分类
     * @param postId 文章 ID
     */
    override suspend fun categoryByPostId(postId: Long): Category? = dbQuery {
        sqlSelectCategory()
            .where { PostCategories.postId eq postId }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 根据分类 ID 获取分类
     * @param id 分类 ID
     */
    override suspend fun category(id: Long): Category? = dbQuery {
        sqlSelectCategory()
            .where { Categories.categoryId eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 根据分类名称获取分类
     * @param displayName 分类名称
     */
    override suspend fun category(displayName: String): Category? = dbQuery {
        sqlSelectCategory()
            .where { Categories.displayName eq displayName }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 根据分类别名获取分类
     * @param slug 分类别名
     */
    override suspend fun categoryBySlug(slug: String): Category? = dbQuery {
        sqlSelectCategory()
            .where { Categories.slug eq slug }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    /**
     * 分类数量
     */
    override suspend fun categoryCount(): Long = dbQuery {
        Categories.selectAll().count()
    }

    /**
     * SQL 语句
     * 获取分类和对应的文章数量
     */
    private fun sqlSelectCategory(): Query {
        return Categories.leftJoin(
            PostCategories,
            additionalConstraint = { Categories.categoryId eq PostCategories.categoryId }
        )
            .select(
                Categories.categoryId, Categories.displayName, Categories.slug,
                Categories.cover, Categories.unifiedCover, PostCategories.categoryId.count()
            )
            .groupBy(Categories.categoryId)
            .orderBy(Categories.categoryId, SortOrder.DESC)
    }
}