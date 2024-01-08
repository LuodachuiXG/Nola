package cc.loac.data.models

import cc.loac.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val body: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime
)