package cc.loac.data.models.tables

import cc.loac.data.models.UserRole
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime


/**
 * 用户表
 * @author Loac
 * @version 1.0, 2024-01-08
 */
object Users : Table("user") {

    /** 用户 ID **/
    val id = integer("id").autoIncrement()

    /** 用户名 **/
    val name = varchar("name", 50)

    /** 电子邮箱 **/
    val email = varchar("email", 50)

    /** 显示名称 **/
    val displayName = varchar("display_name", 100)

    /** 密码 **/
    val password = varchar("password", 100)

    /** 描述 **/
    val description = varchar("description", 1000).nullable()

    /** 注册日期 **/
    val createDate = datetime("create_date")

    /** 头像地址 URL **/
    val avatar = varchar("avatar", 100).nullable()

    /** 用户角色 [UserRole] **/
    val role = enumerationByName<UserRole>("role", 20)


    override val primaryKey = PrimaryKey(id)
}