package cc.loac.services.impl

import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey
import cc.loac.data.sql.dao.ConfigDao
import cc.loac.services.ConfigService
import org.koin.java.KoinJavaComponent.inject



/**
 * “配置”服务接口实现类
 */
class ConfigServiceImpl : ConfigService {

    private val configDao: ConfigDao by inject(ConfigDao::class.java)

    /**
     * 添加配置信息
     * @param config 配置数据类
     */
    override suspend fun addConfig(config: Config): Config? {
        return configDao.addConfig(config)
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
}