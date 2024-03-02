package cc.loac.data.sql.dao

import cc.loac.data.models.Link
import cc.loac.data.requests.LinkRequest

/**
 * 友情链接操作接口
 */
interface LinkDao {

    /**
     * 添加友情链接
     * @param link 友情链接请求数据类
     */
    suspend fun addPost(link: LinkRequest): Link?

    /**
     * 删除友情链接
     * @param ids 友情链接 ID 集合
     */
    suspend fun deleteLinks(ids: List<Int>): Boolean

    /**
     * 修改友情链接
     * @param link 友情链接请求数据类
     */
    suspend fun updateLink(link: LinkRequest): Boolean

}