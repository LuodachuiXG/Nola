package cc.loac.services

import cc.loac.data.models.Link
import cc.loac.data.models.enums.LinkSort
import cc.loac.data.requests.LinkRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.tables.Links

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
    suspend fun deleteLinks(ids: List<Long>): Boolean

    /**
     * 修改友情链接
     * @param link 友情链接请求数据类
     */
    suspend fun updateLink(link: LinkRequest): Boolean

    /**
     * 获取所有友情链接
     * @param sort 友情链接排序
     */
    suspend fun links(sort: LinkSort? = null): List<Link>

    /**
     * 分页获取友情链接
     * @param page 当前页
     * @param size 每页条数
     * @param sort 友情链接排序
     */
    suspend fun links(page: Int, size: Int, sort: LinkSort? = null): Pager<Link>
}