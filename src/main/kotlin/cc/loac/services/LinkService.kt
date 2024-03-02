package cc.loac.services

import cc.loac.data.models.Link
import cc.loac.data.requests.LinkRequest

/**
 * 友情链接服务接口
 */
interface LinkService {

    /**
     * 添加友情链接
     * @param link 友情链接请求数据类
     */
    suspend fun addLink(link: LinkRequest): Link?

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