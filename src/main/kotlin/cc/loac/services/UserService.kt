package cc.loac.services

import cc.loac.data.models.User
import cc.loac.data.responses.AuthResponse
import cc.loac.security.token.TokenConfig

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

    /**
     * 用户登录
     * @param tokenConfig Token 令牌配置
     * @param username 用户名
     * @param password 密码
     */
    suspend fun login(
        tokenConfig: TokenConfig,
        username: String,
        password: String
    ): AuthResponse
}