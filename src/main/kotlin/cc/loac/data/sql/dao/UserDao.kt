package cc.loac.data.sql.dao

import cc.loac.data.models.User

/**
 * 用户表操作接口
 */
interface UserDao {
    /**
     * 获取所有用户
     */
    suspend fun allUsers(): List<User>

    /**
     * 根据用户 ID 获取用户
     * @param id 用户 ID
     */
    suspend fun user(id: Int): User?

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    suspend fun user(username: String): User?

    /**
     * 添加用户
     * @param user 用户数据类
     */
    suspend fun addUser(user: User): User?

    /**
     * 修改用户
     * @param user 用户数据类
     */
    suspend fun updateUser(user: User): Boolean

    /**
     * 删除用户
     * @param id 用户 ID
     */
    suspend fun deleteUser(id: Int): Boolean
}