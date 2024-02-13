package cc.loac.services

import cc.loac.data.models.User

/**
 * 用户服务接口
 */
interface UserService {
    /**
     * 初始化博客管理员
     * @param user 用户数据类
     */
    suspend fun initAdmin(user: User): Boolean

    /**
     * 获取所有用户
     */
    suspend fun allUsers(): List<User>

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    suspend fun user(username: String): User?

    /**
     * 根据用户 ID 修改最后登录时间
     * @param userId 用户 ID
     */
    suspend fun updateLastLoginTime(userId: Int): Boolean
}