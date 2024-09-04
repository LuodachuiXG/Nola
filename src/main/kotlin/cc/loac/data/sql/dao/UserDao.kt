package cc.loac.data.sql.dao

import cc.loac.data.models.User
import cc.loac.data.requests.UserInfoRequest
import cc.loac.security.hashing.SaltedHash

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
    suspend fun user(id: Long): User?

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
     * 修改用户信息
     * @param userId 用户 ID
     * @param userInfo 用户信息
     */
    suspend fun updateUser(userId: Long, userInfo: UserInfoRequest): Boolean

    /**
     * 修改用户密码
     * @param userId 用户 ID
     * @param saltHash 加盐哈希
     */
    suspend fun updatePassword(userId: Long, saltHash: SaltedHash): Boolean

    /**
     * 修改用户最后登录时间
     * @param userId 用户 ID
     */
    suspend fun updateUserLastLoginTime(userId: Long): Boolean

    /**
     * 删除用户
     * @param id 用户 ID
     */
    suspend fun deleteUser(id: Long): Boolean
}