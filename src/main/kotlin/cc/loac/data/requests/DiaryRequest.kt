package cc.loac.data.requests

/**
 * 日记请求数据类
 * @param diaryId 日记 ID
 * @param content 日记内容
 */
data class DiaryRequest(
    val diaryId: Int?,
    val content: String
)