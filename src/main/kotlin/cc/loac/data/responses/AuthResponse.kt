package cc.loac.data.responses


/**
 * 用户登录响应数据类
 * @param username 用户名
 * @param email 电子邮箱
 * @param displayName 显示名称
 * @param description 描述
 * @param createDate 注册日期
 * @param lastLoginDate 最后登录日期
 * @param avatar 头像地址
 * @param token Token 令牌
 */
data class AuthResponse(
    val username: String,
    val email: String,
    val displayName: String,
    val description: String?,
    val createDate: Long,
    val lastLoginDate: Long?,
    val avatar: String?,
    val token: String
)