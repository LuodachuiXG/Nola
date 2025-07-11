package cc.loac.services.impl

import cc.loac.data.models.Link
import cc.loac.data.models.enums.LinkSort
import cc.loac.data.requests.LinkRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.LinkDao
import cc.loac.services.LinkService
import org.koin.java.KoinJavaComponent.inject

/**
 * 友情链接服务接口实现类
 */
class LinkServiceImpl : LinkService {

    private val linkDao: LinkDao by inject(LinkDao::class.java)

    /**
     * 添加友情链接
     * @param link 友情链接请求数据类
     */
    override suspend fun addLink(link: LinkRequest): Link? {
        return linkDao.addPost(link)
    }

    /**
     * 删除友情链接
     * @param ids 友情链接 ID 集合
     */
    override suspend fun deleteLinks(ids: List<Long>): Boolean {
        return linkDao.deleteLinks(ids)
    }

    /**
     * 修改友情链接
     * @param link 友情链接请求数据类
     */
    override suspend fun updateLink(link: LinkRequest): Boolean {
        return linkDao.updateLink(link)
    }

    /**
     * 获取所有友情链接
     * @param sort 友情链接排序
     */
    override suspend fun links(sort: LinkSort?): List<Link> {
        return linkDao.links(sort)
    }

    /**
     * 分页获取友情链接
     * @param page 当前页
     * @param size 每页条数
     * @param sort 友情链接排序
     */
    override suspend fun links(page: Int, size: Int, sort: LinkSort?): Pager<Link> {
        if (page == 0) {
            // 获取所有友情链接
            val links = links(sort)
            return Pager(0, 0, links, links.size.toLong(), 1)
        }
        return linkDao.links(page, size, sort)
    }

    /**
     * 获取友情链接数量
     */
    override suspend fun linkCount(): Long {
        return linkDao.linkCount()
    }
}