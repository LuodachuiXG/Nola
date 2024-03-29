package cc.loac.services

import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey


/**
 * “配置”服务接口
 */
interface ConfigService {
    /**
     * 添加配置信息
     * @param config 配置数据类
     */
    suspend fun addConfig(config: Config): Config?

    /**
     * 删除配置信息
     * @param key 配置键
     */
    suspend fun deleteConfig(key: ConfigKey): Boolean

    /**
     * 修改配置信息
     * @param config 配置数据类
     */
    suspend fun updateConfig(config: Config): Boolean


    /**
     * 获取配置信息
     * @param key 配置键
     */
    suspend fun config(key: ConfigKey): String?
}