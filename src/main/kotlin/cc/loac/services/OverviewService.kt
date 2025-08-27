package cc.loac.services

import cc.loac.data.responses.ApiOverviewResponse
import cc.loac.data.responses.OverviewResponse


/**
 * 概览服务接口
 */
interface OverviewService {

    /**
     * 获取概览信息
     * @param userId 用户ID
     */
    suspend fun getOverview(userId: Long): OverviewResponse

    /**
     * 获取博客 API 概览信息
     */
    suspend fun getApiOverview(): ApiOverviewResponse
}