package cc.loac.data.models

import java.util.Date

/**
 * 用户数据类
 */
data class User(
    /** 用户 ID **/
    val userId: Int = -1,
    /** 用户名 **/
    val username: String,
    /** 电子邮箱 **/
    val email: String,
    /** 显示名称 **/
    val displayName: String,
    /** 密码 **/
    val password: String,
    /** 盐值，用于加密密码 **/
    val salt: String,
    /** 描述 **/
    val description: String? = null,
    /** 注册日期 **/
    val createDate: ULong = Date().time.toULong(),
    /** 最后登录日期 **/
    val lastLoginDate: ULong? = null,
    /** 头像地址 URL **/
    val avatar: String? = null
)
