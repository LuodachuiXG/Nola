package cc.loac.services.impl

import cc.loac.data.models.User
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostRequest
import cc.loac.data.requests.firstPost
import cc.loac.data.sql.dao.UserDao
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.services.PostService
import cc.loac.services.UserService
import cc.loac.utils.launchCoroutine
import org.koin.java.KoinJavaComponent.inject

/**
 * 用户服务接口实现类
 */
class UserServiceImpl : UserService {

    private val hashingService = SHA256HashingService()
    private val postService: PostService by inject(PostService::class.java)
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
        val result = userDao.addUser(u) != null
        if (result) {
            launchCoroutine {
                // 管理员初始化完成，判断是否有文章，没有的话就插入默认文章
                if (postService.postCount() == 0L) {
                    val postRequest = firstPost()
                    postService.addPost(postRequest)
                }
            }
        }

        // 添加用户
        return result
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