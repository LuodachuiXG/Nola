package cc.loac.data.models

import cc.loac.data.serializer.LocalDateTimeSerializer
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * 用户实体类
 */
data class User(
    val id: Int = -1,
    val name: String,
    val email: String,
    val displayName: String,
    val password: String,
    val salt: String,
    val description: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createDate: LocalDateTime = LocalDateTime.now(),
    val avatar: String? = null
)
