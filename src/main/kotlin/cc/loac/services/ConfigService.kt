package cc.loac.services

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.Config
import cc.loac.data.models.enums.ConfigKey


/**
 * “配置”服务接口
 */
interface ConfigService {
    /**
     * 添加配置信息
     * @param config [Config] 实体类，包含 key and value
     */
    suspend fun addConfig(config: Config): Config?


    /**
     * 获取配置信息
     * @param key 枚举类 [ConfigKey] 传入配置键
     */
    suspend fun config(key: ConfigKey): String?
}