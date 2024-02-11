package cc.loac.services

import cc.loac.data.models.BlogInfo


/**
 * “配置”服务接口
 */
interface ConfigService {
    /**
     * 获取博客基础信息
     * @return [BlogInfo]
     */
    suspend fun blogInfo(): BlogInfo?
}