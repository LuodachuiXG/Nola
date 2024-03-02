package cc.loac.services.impl

import cc.loac.data.models.Link
import cc.loac.data.requests.LinkRequest
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
    override suspend fun deleteLinks(ids: List<Int>): Boolean {
        return linkDao.deleteLinks(ids)
    }
}