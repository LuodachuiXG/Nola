package cc.loac.data.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.Date

/**
 * 用户数据类
 * @param userId 用户 ID
 * @param username 用户名
 * @param email 电子邮箱
 * @param displayName 显示名称
 * @param password 密码
 * @param salt 盐值，用于加密密码
 * @param description 描述
 * @param createDate 注册日期
 * @param lastLoginDate 最后登录日期
 * @param avatar 头像地址 URL
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class User(
    val userId: Long = -1,
    val username: String,
    val email: String,
    val displayName: String,
    val password: String,
    val salt: String,
    val description: String? = null,
    val createDate: Long = Date().time,
    val lastLoginDate: Long? = null,
    val avatar: String? = null
)
