package cc.loac.services.impl

import cc.loac.data.sql.dao.CommentDao
import cc.loac.services.CommentService
import org.koin.java.KoinJavaComponent.inject

/**
 * 评论服务接口实现类
 */
class CommentServiceImpl: CommentService {
    private val commentDao: CommentDao by inject(CommentDao::class.java)
}