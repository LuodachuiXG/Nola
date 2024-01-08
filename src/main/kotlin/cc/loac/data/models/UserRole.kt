package cc.loac.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 用户角色枚举类
 * @author Loac
 * @version 1.0 2024-01-08
 */
@Serializable
enum class UserRole {
    @SerialName("super_admin")
    SUPER_ADMIN,
    @SerialName("admin")
    ADMIN,
    @SerialName("guest")
    GUEST
}