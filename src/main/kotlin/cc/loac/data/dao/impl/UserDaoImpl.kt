package cc.loac.data.dao.impl

import cc.loac.data.DatabaseSingleton.dbQuery
import cc.loac.data.dao.UserDao
import cc.loac.data.models.User
import cc.loac.data.models.tables.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


val userDao: UserDao = UserDaoImpl()

/**
 * 用户表操作接口实现类
 * @author Loac
 * @version 1.0 2024-01-09
 */
class UserDaoImpl : UserDao {

    /**
     * 将数据库检索结果转为 [User] 实体类
     */
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        email = row[Users.email],
        displayName = row[Users.displayName],
        password = row[Users.password],
        description = row[Users.description],
        createDate = row[Users.createDate],
        avatar = row[Users.avatar],
        role = row[Users.role]
    )

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users
            .select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun addUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[name] = user.name
            it[email] = user.email
            it[displayName] = user.displayName
            it[password] = user.password
            it[description] = user.description
            it[createDate] = user.createDate
            it[avatar] = user.avatar
            it[role] = user.role
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun editUser(user: User): Boolean = dbQuery {
        Users.update({
            Users.id eq user.id
        }) {
            it[displayName] = user.displayName
            it[description] = user.description
            it[avatar] = user.avatar
            it[role] = user.role
        } > 0
    }

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}