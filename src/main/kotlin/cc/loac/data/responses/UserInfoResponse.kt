package cc.loac.data.responses

import cc.loac.data.models.User

/**
 * 用户信息响应数据类
 * @param userId 用户 ID
 * @param username 用户名
 * @param email 电子邮箱
 * @param displayName 显示名称
 * @param description 描述
 * @param avatar 头像地址 URL
 * @param createDate 注册日期
 * @param lastLoginDate 最后登录日期
 */
data class UserInfoResponse(
    val userId: Int,
    val username: String,
    val email: String,
    val displayName: String,
    val description: String?,
    val avatar: String?,
    val createDate: Long,
    val lastLoginDate: Long?
)

/**
 * 将用户数据类转换为用户信息响应数据类
 */
fun User.toUserInfoResponse(): UserInfoResponse {
    return UserInfoResponse(
        userId = userId,
        username = username,
        email = email,
        displayName = displayName,
        description = description,
        avatar = avatar,
        createDate = createDate,
        lastLoginDate = lastLoginDate
    )
}