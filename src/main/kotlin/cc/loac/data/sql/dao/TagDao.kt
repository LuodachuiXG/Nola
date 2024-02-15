package cc.loac.data.sql.dao

import cc.loac.data.models.Tag

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
     * 根据标签名称获取标签
     * @param displayName 标签名称
     */
    suspend fun tag(displayName: String): Tag?
}