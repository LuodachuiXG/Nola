package cc.loac.services.impl

import cc.loac.data.models.User
import cc.loac.data.sql.dao.UserDao
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.services.UserService
import org.koin.java.KoinJavaComponent.inject

/**
 * 用户服务接口实现类
 */
class UserServiceImpl : UserService {

    private val hashingService = SHA256HashingService()
    private val userDao: UserDao by inject(UserDao::class.java)

    /**
     * 初始化博客管理员
     * @param user 用户数据类
     */
    override suspend fun initAdmin(user: User): Boolean {
        // 对密码生成加盐哈希
        val saltHash = hashingService.generatedSaltedHash(user.password)
        val u = user.copy(
            password = saltHash.hash,
            salt = saltHash.salt
        )
        // 添加用户
        return userDao.addUser(u) != null
    }

    /**
     * 获取所有用户
     */
    override suspend fun allUsers(): List<User> {
        return userDao.allUsers()
    }

    /**
     * 根据用户名获取用户
     * @param username 用户名
     */
    override suspend fun user(username: String): User? {
        return userDao.user(username)
    }

    /**
     * 根据用户 ID 修改最后登录时间
     * @param userId 用户 ID
     */
    override suspend fun updateLastLoginTime(userId: Int): Boolean {
        return userDao.updateUserLastLoginTime(userId)
    }
}