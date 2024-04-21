package cc.loac.data.responses

import cc.loac.data.models.User

/**
 * 博主响应数据类
 * @param email 电子邮箱
 * @param displayName 显示名称
 * @param description 描述
 * @param avatar 头像地址 URL
 */
data class BloggerResponse(
    val email: String,
    val displayName: String,
    val description: String?,
    val avatar: String?
)

/**
 * 将用户数据类转换为博主响应数据类
 */
fun User.toBloggerResponse(): BloggerResponse {
    return BloggerResponse(
        email = email,
        displayName = displayName,
        description = description,
        avatar = avatar
    )
}