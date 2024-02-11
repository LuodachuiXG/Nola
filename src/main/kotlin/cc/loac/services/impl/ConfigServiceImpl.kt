package cc.loac.services.impl

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.sql.dao.ConfigDao
import cc.loac.services.ConfigService
import cc.loac.utils.jsonToClass
import org.koin.java.KoinJavaComponent.inject

val configDao: ConfigDao by inject(ConfigDao::class.java)

/**
 * “配置”服务接口实现类
 */
class ConfigServiceImpl : ConfigService {
    /**
     * 获取博客基础信息
     * @return [BlogInfo]
     */
    override suspend fun blogInfo(): BlogInfo? {
        val res = configDao.config(ConfigKey.BLOG_INFO) ?: return null
        return res.jsonToClass()
    }
}