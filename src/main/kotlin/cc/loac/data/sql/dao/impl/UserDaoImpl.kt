package cc.loac.data.sql.dao.impl

import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.UserDao
import cc.loac.data.models.User
import cc.loac.data.sql.tables.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

/** 实现用户表操作接口 **/
val userDao: UserDao = UserDaoImpl()

/**
 * 用户表操作接口实现类
 */
class UserDaoImpl : UserDao {

    /**
     * 将数据库检索结果转为 [User] 实体类
     */
    private fun resultRowToUser(row: ResultRow) = User(
        userId = row[Users.userId],
        username = row[Users.username],
        email = row[Users.email],
        displayName = row[Users.displayName],
        salt = row[Users.salt],
        password = row[Users.password],
        description = row[Users.description],
        createDate = row[Users.createDate],
        lastLoginDate = row[Users.lastLoginDate],
        avatar = row[Users.avatar],
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users
            .select { Users.userId eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun user(name: String): User? = dbQuery {
        Users
            .select { Users.username eq name }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun addUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[username] = user.username
            it[email] = user.email
            it[displayName] = user.displayName
            it[password] = user.password
            it[salt] = user.salt
            it[description] = user.description
            it[createDate] = user.createDate
            it[avatar] = user.avatar
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun editUser(user: User): Boolean = dbQuery {
        Users.update({
            Users.userId eq user.userId
        }) {
            it[displayName] = user.displayName
            it[description] = user.description
            it[avatar] = user.avatar
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.userId eq id } > 0
    }
}