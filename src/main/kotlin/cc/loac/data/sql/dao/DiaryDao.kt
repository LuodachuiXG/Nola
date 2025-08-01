package cc.loac.data.sql.dao

import cc.loac.data.models.Diary
import cc.loac.data.models.enums.DiarySort
import cc.loac.data.requests.DiaryRequest
import cc.loac.data.responses.Pager

/**
 * 日记表操作接口
 */
interface DiaryDao {

    /**
     * 增加日记
     * @param diary 日记请求数据类
     */
    suspend fun addDiary(diary: DiaryRequest): Diary?

    /**
     * 删除日记
     * @param diaryIds 日记 ID 数组
     */
    suspend fun deleteDiaries(diaryIds: List<Long>): Boolean

    /**
     * 更新日记
     * @param diary 日记请求数据类
     */
    suspend fun updateDiary(diary: DiaryRequest): Boolean

    /**
     * 获取所有日记
     * @param sort 日记排序
     */
    suspend fun diaries(sort: DiarySort?): List<Diary>

    /**
     * 分页获取日志
     * @param page 当前页
     * @param size 每页条数
     * @param sort 日志排序
     */
    suspend fun diaries(page: Int, size: Int, sort: DiarySort?): Pager<Diary>

    /**
     * 日记数量
     */
    suspend fun diaryCount(): Long

}