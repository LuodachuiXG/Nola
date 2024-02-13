package cc.loac.data.responses


/**
 * 用户登录响应数据类
 */
data class AuthResponse(
    /** 用户名 **/
    val username: String,
    /** 电子邮箱 **/
    val email: String,
    /** 显示名称 **/
    val displayName: String,
    /** 描述 **/
    val description: String?,
    /** 注册日期 **/
    val createDate: Long,
    /** 最后登录日期 **/
    val lastLoginDate: Long?,
    /** 头像地址 **/
    val avatar: String?,
    /** Token 令牌 **/
    val token: String
)