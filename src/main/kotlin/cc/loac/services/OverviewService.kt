package cc.loac.services

import cc.loac.data.responses.OverviewResponse


/**
 * 概述服务接口
 */
interface OverviewService {

    /**
     * 获取概述信息
     * @param userId 用户ID
     */
    suspend fun getOverview(userId: Long): OverviewResponse
}