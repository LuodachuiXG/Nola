package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Tag
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.TagDao
import cc.loac.services.TagService
import org.koin.java.KoinJavaComponent.inject

/**
 * 标签服务接口实现类
 */
class TagServiceImpl : TagService {

    private val tagDao: TagDao by inject(TagDao::class.java)

    /**
     * 添加标签
     * @param tag 标签数据类
     */
    override suspend fun addTag(tag: Tag): Tag? {
        // 先判断标签别名是否已经存在
        if (isSlugExist(tag)) {
            throw MyException("标签别名 [${tag.slug}] 已经存在")
        }
        return tagDao.addTag(tag)
    }

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    override suspend fun deleteTags(tagIds: List<Long>): Boolean {
        return tagDao.deleteTags(tagIds)
    }

    /**
     * 根据别名删除标签
     * @param slugs 标签别名集合
     */
    override suspend fun deleteTagsBySlugs(slugs: List<String>): Boolean {
        return tagDao.deleteTagsBySlugs(slugs)
    }

    /**
     * 修改标签
     * @param tag 标签数据类
     */
    override suspend fun updateTag(tag: Tag): Boolean {
        if (isSlugExist(tag)) {
            throw MyException("标签别名 [${tag.slug}] 已经存在")
        }
        return tagDao.updateTag(tag)
    }

    /**
     * 获取所有标签
     */
    override suspend fun tags(): List<Tag> {
        return tagDao.tags()
    }

    /**
     * 获取文章数量最多的 6 个标签
     */
    override suspend fun topTags(): List<Tag> {
        return tagDao.topTags()
    }

    /**
     * 分页获取所有标签
     * @param page 当前页
     * @param size 每页条数
     */
    override suspend fun tags(page: Int, size: Int): Pager<Tag> {
        if (page == 0) {
            // 获取所有标签
            val tags = tags()
            return Pager(0, 0, tags, tags.size.toLong(), tags.size.toLong())
        }
        return tagDao.tags(page, size)
    }

    /**
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    override suspend fun tag(tagId: Long): Tag? {
        return tagDao.tag(tagId)
    }

    /**
     * 根据标签名称获取标签
     * @param displayName 标签名称
     */
    override suspend fun tag(displayName: String): Tag? {
        return tagDao.tag(displayName)
    }

    /**
     * 根据标签别名获取标签
     * @param slug 标签别名
     */
    override suspend fun tagBySlug(slug: String): Tag? {
        return tagDao.tagBySlug(slug)
    }

    /**
     * 判断标签别名是否已经存在，并且不是当前标签自己
     * @param tag 标签数据类
     */
    override suspend fun isSlugExist(tag: Tag): Boolean {
        // 判断标签别名是否已经存在，并且不是当前标签
        val t = tagBySlug(tag.slug)
        return t != null && t.tagId != tag.tagId
    }

    /**
     * 根据标签 ID 集合，判断标签是否都存在
     * @param tagIds 标签 ID 集合
     * @return 如果标签都存在返回空集合，否则返回不存在的 ID 集合
     */
    override suspend fun isIdsExist(tagIds: List<Long>): List<Long> {
        val tags = tagDao.tags(tagIds)
        // 标签都存在，返回空集合
        if (tags.size == tagIds.size) return emptyList()

        // 检查哪些标签不存在
        val nonExistIds = mutableListOf<Long>()
        tagIds.forEach { id ->
            if (tags.none { it.tagId == id }) {
                nonExistIds.add(id)
            }
        }
        // 返回不存在的标签的 ID
        return nonExistIds
    }

    /**
     * 标签数量
     */
    override suspend fun tagCount(): Long {
        return tagDao.tagCount()
    }

}