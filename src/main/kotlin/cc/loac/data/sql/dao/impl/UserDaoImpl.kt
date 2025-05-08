package cc.loac.data.sql.dao.impl

import cc.loac.data.sql.DatabaseSingleton.dbQuery
import cc.loac.data.sql.dao.UserDao
import cc.loac.data.models.User
import cc.loac.data.requests.UserInfoRequest
import cc.loac.data.sql.tables.Users
import cc.loac.security.hashing.SaltedHash
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.Date

/**
 * 用户表操作接口实现类
 */
class UserDaoImpl : UserDao {

    /**
     * 将数据库检索结果转为 [User] 用户数据类
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

    /**
     * 获取所有用户
     */
    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }

    /**
     * 根据用户 ID 获取用户
     * @param id 用户 ID
     */
    override suspend fun user(id: Long): User? = dbQuery {
        Users
            .selectAll().where { Users.userId eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    override suspend fun user(username: String): User? = dbQuery {
        Users.selectAll().where { Users.username eq username }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    /**
     * 添加用户
     * @param user 用户数据类
     */
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

    /**
     * 修改用户信息
     * @param userId 用户 ID
     * @param userInfo 用户信息
     */
    override suspend fun updateUser(userId: Long, userInfo: UserInfoRequest): Boolean = dbQuery {
        Users.update({
            Users.userId eq userId
        }) {
            it[username] = userInfo.username
            it[email] = userInfo.email
            it[displayName] = userInfo.displayName
            it[description] = userInfo.description
            it[avatar] = if (userInfo.avatar.isNullOrBlank()) null else userInfo.avatar
        } > 0
    }

    /**
     * 修改用户密码
     * @param userId 用户 ID
     * @param saltHash 加盐哈希
     */
    override suspend fun updatePassword(userId: Long, saltHash: SaltedHash): Boolean = dbQuery {
        Users.update({
            Users.userId eq userId
        }) {
            it[password] = saltHash.hash
            it[salt] = saltHash.salt
        } > 0
    }

    /**
     * 修改用户最后登录时间
     * @param userId 用户 ID
     */
    override suspend fun updateUserLastLoginTime(userId: Long): Boolean = dbQuery {
        Users.update({
            Users.userId eq userId
        }) {
            it[lastLoginDate] = Date().time
        } > 0
    }

    /**
     * 删除用户
     * @param id 用户 ID
     */
    override suspend fun deleteUser(id: Long): Boolean = dbQuery {
        Users.deleteWhere { userId eq id } > 0
    }

    /**
     * 获取最后登录时间
     */
    override suspend fun lastLoginDate(userId: Long): Long? = dbQuery {
        Users.select(Users.lastLoginDate)
            .where { Users.userId eq userId }
            .map { it[Users.lastLoginDate] }
            .singleOrNull()
    }
}