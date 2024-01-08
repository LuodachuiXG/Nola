package cc.loac.data.models

import cc.loac.data.serializer.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

/**
 * 用户实体类
 * @author Loac
 * @version 1.0 2024-01-08
 */
@Serializable
data class User(
    val id: Int = -1,
    val name: String,
    val email: String,
    val displayName: String,
    val password: String,
    val description: String? = null,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createDate: LocalDateTime = LocalDateTime.now(),
    val avatar: String? = null,
    val role: UserRole
)
