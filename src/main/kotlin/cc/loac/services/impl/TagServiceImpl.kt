package cc.loac.services.impl

import cc.loac.data.models.Tag
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
        return tagDao.addTag(tag)
    }

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    override suspend fun deleteTag(tagIds: List<Int>): Boolean {
        return tagDao.deleteTag(tagIds)
    }

    /**
     * 修改标签
     * @param tag 标签数据类
     */
    override suspend fun updateTag(tag: Tag): Boolean {
        return tagDao.updateTag(tag)
    }

    /**
     * 获取所有标签
     */
    override suspend fun tags(): List<Tag> {
        return tagDao.tags()
    }

    /**
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    override suspend fun tag(tagId: Int): Tag? {
        return tagDao.tag(tagId)
    }

    /**
     * 根据标签显示名称获取标签
     * @param displayName 标签显示名称
     */
    override suspend fun tag(displayName: String): Tag? {
        return tagDao.tag(displayName)
    }

}