package cc.loac.data.requests

/**
 * 用户信息请求数据类
 * @param username 用户名
 * @param email 电子邮箱
 * @param displayName 显示名称
 * @param description 描述
 * @param avatar 头像地址 URL
 */
data class UserInfoRequest(
    val username: String,
    val email: String,
    val displayName: String,
    val description: String?,
    val avatar: String?
)