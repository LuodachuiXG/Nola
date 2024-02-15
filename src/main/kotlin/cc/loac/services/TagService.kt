package cc.loac.services

import cc.loac.data.models.Tag

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
    suspend fun deleteTag(tagIds: List<Int>): Boolean

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
     * 根据标签 ID 获取标签
     * @param tagId 标签 ID
     */
    suspend fun tag(tagId: Int): Tag?

    /**
     * 根据标签显示名称获取标签
     * @param displayName 标签显示名称
     */
    suspend fun tag(displayName: String): Tag?
}