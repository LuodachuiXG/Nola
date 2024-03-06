package cc.loac.data.sql.dao

import cc.loac.data.models.Tag
import cc.loac.data.responses.Pager

/**
 * 标签表操作接口
 */
interface TagDao {
    /**
     * 添加标签
     * @param tag 标签数据类
     */
    suspend fun addTag(tag: Tag): Tag?

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    suspend fun deleteTags(tagIds: List<Int>): Boolean

    /**
     * 根据别名删除标签
     * @param slugs 标签别名集合
     */
    suspend fun deleteTagsBySlugs(slugs: List<String>): Boolean

    /**
     * 修改标签
     * @param tag 标签数据类
     */
    suspend fun updateTag(tag: Tag): Boolean

    /**
     * 获取所有标签
     */
    suspend fun tags(): List<Tag>

    /**
     * 分页获取所有标签
     * @param page 当前页
     * @param size 每页条数
     */
    suspend fun tags(page: Int, size: Int): Pager<Tag>

    /**
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    suspend fun tag(tagId: Int): Tag?

    /**
     * 根据文章 ID 获取标签
     */
    suspend fun tags(postId: Int): List<Tag>

    /**
     * 根据标签 ID 集合获取标签
     * @param tagIds 标签 ID 集合
     */
    suspend fun tags(tagIds: List<Int>): List<Tag>

    /**
     * 根据标签名称获取标签
     * @param displayName 标签名称
     */
    suspend fun tag(displayName: String): Tag?

    /**
     * 根据标签别名获取标签
     * @param slug 标签别名
     */
    suspend fun tagBySlug(slug: String): Tag?
}