package cc.loac.data.models

/**
 * 日记数据类
 * @param diaryId 日记 ID
 * @param content 日记内容
 * @param html HTML（由 content 解析得来）
 * @param createTime 创建时间
 * @param lastModifyTime 最后修改时间
 */
data class Diary(
    val diaryId: Int,
    val content: String,
    val html: String,
    val createTime: Long,
    val lastModifyTime: Long?
)
