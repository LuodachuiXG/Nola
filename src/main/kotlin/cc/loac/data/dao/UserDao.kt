package cc.loac.data.dao

import cc.loac.data.models.User

/**
 * 用户表操作接口
 * @author Loac
 * @version 1.0 2024-01-09
 */
interface UserDao {
    suspend fun allUsers(): List<User>
    suspend fun user(id: Int): User?
    suspend fun user(name: String): User?
    suspend fun addUser(user: User): User?
    suspend fun editUser(user: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
}