package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table


/**
 * 用户表
 */
object Users : Table("user") {

    /** 用户 ID **/
    val userId = integer("userId").autoIncrement()

    /** 用户名 **/
    val username = varchar("username", 64, "utf8mb4_general_ci").uniqueIndex()

    /** 电子邮箱 **/
    val email = varchar("email", 64)

    /** 显示名称 **/
    val displayName = varchar("display_name", 128)

    /** 密码 **/
    val password = varchar("password", 128)

    /** 盐值，用于加密密码 **/
    val salt = varchar("salt", 128)

    /** 描述 **/
    val description = varchar("description", 1024).nullable()

    /** 注册日期 **/
    val createDate = long("create_date")

    /** 最后登录日期 **/
    val lastLoginDate = long("last_login_time").nullable()

    /** 头像地址 URL **/
    val avatar = varchar("avatar", 512).nullable()


    override val primaryKey = PrimaryKey(userId)
}