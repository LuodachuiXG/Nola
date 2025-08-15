package cc.loac.services

import cc.loac.data.models.Tag
import cc.loac.data.responses.Pager

/**
 * 标签服务接口
 */
interface TagService {

    /**
     * 添加标签
     * @param tag 标签数据类
     */
    suspend fun addTag(tag: Tag): Tag?

    /**
     * 删除标签
     * @param tagIds 标签 ID 集合
     */
    suspend fun deleteTags(tagIds: List<Long>): Boolean

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
     * 获取文章数量最多的 6 个标签
     */
    suspend fun topTags(): List<Tag>

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
    suspend fun tag(tagId: Long): Tag?

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

    /**
     * 判断标签别名是否已经存在，并且不是当前标签自己
     * @param tag 标签数据类
     */
    suspend fun isSlugExist(tag: Tag): Boolean

    /**
     * 根据标签 ID 集合，判断标签是否都存在
     * @param tagIds 标签 ID 集合
     * @return 如果标签都存在返回空集合，否则返回不存在的 ID 集合
     */
    suspend fun isIdsExist(tagIds: List<Long>): List<Long>

    /**
     * 标签数量
     */
    suspend fun tagCount(): Long
}