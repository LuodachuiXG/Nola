package cc.loac.services

import cc.loac.data.models.BlogInfo
import cc.loac.data.models.Config
import cc.loac.data.models.ICPFiling
import cc.loac.data.models.enums.ConfigKey


/**
 * “配置”服务接口
 */
interface ConfigService {
    /**
     * 设置配置信息
     * @param config 配置数据类
     */
    suspend fun setConfig(config: Config): Config?

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

    /**
     * 设置博客信息
     * @param blogInfo 博客信息数据类
     */
    suspend fun setBlogInfo(blogInfo: BlogInfo): Boolean

    /**
     * 获取博客信息
     */
    suspend fun blogInfo(): BlogInfo?


    /**
     * 设置备案信息
     * @param icpFiling 备案信息数据类
     */
    suspend fun setICPFiling(icpFiling: ICPFiling): Boolean

    /**
     * 获取备案信息
     */
    suspend fun icpFiling(): ICPFiling?
}