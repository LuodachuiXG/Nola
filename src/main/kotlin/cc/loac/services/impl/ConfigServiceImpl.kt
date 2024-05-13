package cc.loac.services.impl

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.Config
import cc.loac.data.models.ICPFiling
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.sql.dao.ConfigDao
import cc.loac.services.ConfigService
import cc.loac.utils.jsonToClass
import cc.loac.utils.toJSONString
import org.koin.java.KoinJavaComponent.inject


/**
 * “配置”服务接口实现类
 */
class ConfigServiceImpl : ConfigService {

    private val configDao: ConfigDao by inject(ConfigDao::class.java)

    /**
     * 设置配置信息
     * @param config 配置数据类
     */
    override suspend fun setConfig(config: Config): Config? {
        return if (config(config.key) != null) {
            // 配置已经存在，修改配置
            if (updateConfig(config)) config else null
        } else {
            // 配置不存在，添加配置
            configDao.addConfig(config)
        }
    }

    /**
     * 删除配置信息
     * @param key 配置键
     */
    override suspend fun deleteConfig(key: ConfigKey): Boolean {
        return configDao.deleteConfig(key)
    }

    /**
     * 修改配置信息
     * @param config 配置数据类
     */
    override suspend fun updateConfig(config: Config): Boolean {
        return configDao.updateConfig(config)
    }

    /**
     * 获取配置信息
     * @param key 配置键
     */
    override suspend fun config(key: ConfigKey): String? {
        return configDao.config(key)
    }

    /**
     * 设置博客信息
     * @param blogInfo 博客信息数据类
     */
    override suspend fun setBlogInfo(blogInfo: BlogInfo): Boolean {
        return setConfig(Config(key = ConfigKey.BLOG_INFO, value = blogInfo.toJSONString())) != null
    }

    /**
     * 获取博客信息
     */
    override suspend fun blogInfo(): BlogInfo? {
        return config(ConfigKey.BLOG_INFO)?.jsonToClass()
    }

    /**
     * 设置备案信息
     * @param icpFiling 备案信息数据类
     */
    override suspend fun setICPFiling(icpFiling: ICPFiling): Boolean {
        return setConfig(Config(key = ConfigKey.ICP_FILING, value = icpFiling.toJSONString())) != null
    }

    /**
     * 获取备案信息
     */
    override suspend fun icpFiling(): ICPFiling? {
        return config(ConfigKey.ICP_FILING)?.jsonToClass()
    }
}