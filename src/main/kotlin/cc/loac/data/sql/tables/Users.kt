package cc.loac.data.sql.tables

import org.jetbrains.exposed.sql.Table


/**
 * 用户表
 */
object Users : Table("user") {

    /** 用户 ID **/
    val userId = integer("userId").autoIncrement()

    /** 用户名 **/
    val username = varchar("username", 50)

    /** 电子邮箱 **/
    val email = varchar("email", 50)

    /** 显示名称 **/
    val displayName = varchar("display_name", 100)

    /** 密码 **/
    val password = varchar("password", 100)

    /** 盐值，用于加密密码 **/
    val salt = varchar("salt", 100)

    /** 描述 **/
    val description = varchar("description", 1000).nullable()

    /** 注册日期 **/
    val createDate = ulong("create_date")

    /** 最后登录日期 **/
    val lastLoginDate = ulong("last_login_time").nullable()

    /** 头像地址 URL **/
    val avatar = varchar("avatar", 100).nullable()


    override val primaryKey = PrimaryKey(userId)
}