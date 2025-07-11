package cc.loac.data.sql.dao

import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey

/**
 * 配置表操作接口
 */
interface ConfigDao {
    /**
     * 增加配置
     * @param config 配置数据类
     */
    suspend fun addConfig(config: Config): Config?

    /**
     * 删除配置
     * @param key 配置表键
     */
    suspend fun deleteConfig(key: ConfigKey): Boolean

    /**
     * 更新配置
     * @param config 配置数据类
     */
    suspend fun updateConfig(config: Config): Boolean

    /**
     * 获取配置
     * @param key 配置键
     */
    suspend fun config(key: ConfigKey): String?
}