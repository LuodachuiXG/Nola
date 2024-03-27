package cc.loac.data.sql.dao.impl

import cc.loac.data.models.Diary
import cc.loac.data.models.enums.DiarySort
import cc.loac.data.requests.DiaryRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.DiaryDao
import cc.loac.data.sql.startPage
import cc.loac.data.sql.tables.Diaries
import cc.loac.utils.markdownToHtml
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import java.util.Date

/**
 * 日记表操作接口实现类
 */
class DiaryDaoImpl : DiaryDao {

    /**
     * 将数据库检索结果转为 [Diary] 日记数据类
     */
    private fun resultRowToDiary(row: ResultRow) = Diary(
        diaryId = row[Diaries.diaryId],
        content = row[Diaries.content],
        html = row[Diaries.html],
        createTime = row[Diaries.createTime],
        lastModifyTime = row[Diaries.lastModifyTime]
    )

    /**
     * 增加日记
     * @param diary 日记请求数据类
     */
    override suspend fun addDiary(diary: DiaryRequest): Diary? = dbQuery {
        Diaries.insert {
            it[content] = diary.content
            it[html] = diary.content.markdownToHtml()
            it[createTime] = Date().time
            it[lastModifyTime] = null
        }.resultedValues?.singleOrNull()?.let(::resultRowToDiary)
    }

    /**
     * 删除日记
     * @param diaryIds 日记 ID 数组
     */
    override suspend fun deleteDiaries(diaryIds: List<Int>): Boolean = dbQuery {
        Diaries.deleteWhere {
            diaryId inList diaryIds
        } > 0
    }

    /**
     * 更新日记
     * @param diary 日记请求数据类
     */
    override suspend fun updateDiary(diary: DiaryRequest): Boolean = dbQuery {
        Diaries.update({
            Diaries.diaryId eq diary.diaryId!!
        }) {
            it[content] = diary.content
            it[html] = diary.content.markdownToHtml()
            it[lastModifyTime] = Date().time
        } > 0
    }

    /**
     * 获取所有日记
     * @param sort 日记排序
     */
    override suspend fun diaries(sort: DiarySort?): List<Diary> = dbQuery {
        sqlQueryDiaries(sort).map(::resultRowToDiary)
    }

    /**
     * 分页获取日志
     * @param page 当前页
     * @param size 每页条数
     * @param sort 日志排序
     */
    override suspend fun diaries(page: Int, size: Int, sort: DiarySort?): Pager<Diary> {
        return Diaries.startPage(page, size, ::resultRowToDiary) {
            sqlQueryDiaries(sort)
        }
    }

    /**
     * SQL 语句
     * 查询日记
     * @param sort 日记排序
     */
    private fun sqlQueryDiaries(sort: DiarySort?): Query {
        val query = Diaries.selectAll()
        when (sort) {
            DiarySort.CREATE_TIME_DESC -> query.orderBy(Diaries.createTime, SortOrder.DESC)
            DiarySort.CREATE_TIME_ASC -> query.orderBy(Diaries.createTime, SortOrder.ASC)
            DiarySort.MODIFY_TIME_DESC -> query.orderBy(Diaries.lastModifyTime, SortOrder.DESC)
            DiarySort.MODIFY_TIME_ASC -> query.orderBy(Diaries.lastModifyTime, SortOrder.ASC)
            // 默认创建时间降序
            else -> query.orderBy(Diaries.createTime, SortOrder.DESC)
        }
        return query
    }
}