package cc.loac.services

import cc.loac.data.models.User

/**
 * 用户服务接口
 */
interface UserService {
    /**
     * 初始化博客管理员
     */
    suspend fun initAdmin(user: User): Boolean

    /**
     * 获取所有用户
     */
    suspend fun allUsers(): List<User>

    /**
     * 根据用户名获取用户
     */
    suspend fun user(username: String): User?
}