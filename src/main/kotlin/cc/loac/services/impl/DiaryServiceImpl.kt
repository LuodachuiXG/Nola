package cc.loac.services.impl

import cc.loac.data.models.Diary
import cc.loac.data.models.enums.DiarySort
import cc.loac.data.requests.DiaryRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.DiaryDao
import cc.loac.services.DiaryService
import org.koin.java.KoinJavaComponent.inject

/**
 * 日记服务实现类
 */
class DiaryServiceImpl : DiaryService {
    private val diaryDao: DiaryDao by inject(DiaryDao::class.java)

    /**
     * 增加日记
     * @param diary 日记请求数据类
     */
    override suspend fun addDiary(diary: DiaryRequest): Diary? {
        return diaryDao.addDiary(diary)
    }

    /**
     * 删除日记
     * @param diaryIds 日记 ID 数组
     */
    override suspend fun deleteDiaries(diaryIds: List<Long>): Boolean {
        return diaryDao.deleteDiaries(diaryIds)
    }

    /**
     * 更新日记
     * @param diary 日记请求数据类
     */
    override suspend fun updateDiary(diary: DiaryRequest): Boolean {
        return diaryDao.updateDiary(diary)
    }

    /**
     * 获取所有日记
     * @param sort 日记排序
     */
    override suspend fun diaries(sort: DiarySort?): List<Diary> {
        return diaryDao.diaries(sort)
    }

    /**
     * 分页获取日志
     * @param page 当前页
     * @param size 每页条数
     * @param sort 日志排序
     */
    override suspend fun diaries(page: Int, size: Int, sort: DiarySort?): Pager<Diary> {
        if (page == 0) {
            // 获取所有日记
            val diaries = diaries(sort)
            return Pager(0, 0, diaries, diaries.size.toLong(), 1)
        }
        return diaryDao.diaries(page, size, sort)
    }

    /**
     * 日记数量
     */
    override suspend fun diaryCount(): Long {
        return diaryDao.diaryCount()
    }
}