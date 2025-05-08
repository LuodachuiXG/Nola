package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.User
import cc.loac.data.models.enums.MenuItemTarget
import cc.loac.data.requests.MenuItemRequest
import cc.loac.data.requests.MenuRequest
import cc.loac.data.requests.UserInfoRequest
import cc.loac.data.requests.firstPost
import cc.loac.data.responses.AuthResponse
import cc.loac.data.sql.dao.UserDao
import cc.loac.extensions.isAlphaAndNumeric
import cc.loac.extensions.isEmail
import cc.loac.security.hashing.HashingService
import cc.loac.security.hashing.SaltedHash
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import cc.loac.services.MenuService
import cc.loac.services.PostService
import cc.loac.services.UserService
import cc.loac.utils.operate
import cc.loac.utils.operateSync
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

/**
 * 用户服务接口实现类
 */
class UserServiceImpl : UserService {

    private val postService: PostService by inject(PostService::class.java)
    private val menuService: MenuService by inject(MenuService::class.java)
    private val hashingService: HashingService by inject(HashingService::class.java)
    private val tokenService: TokenService by inject(TokenService::class.java)
    private val userDao: UserDao by inject(UserDao::class.java)

    private val ioScope = CoroutineScope(Dispatchers.IO)

    /**
     * 初始化博客管理员
     * @param user 用户数据类
     * @param ip 客户端 IP
     */
    override suspend fun initAdmin(user: User, ip: String): Boolean {
        // 判断是否已经初始了管理员
        if (allUsers().isNotEmpty()) {
            throw MyException("管理员已经创建")
        }

        if (!user.username.isAlphaAndNumeric()) {
            throw MyException("用户名只支持英文和数字")
        }

        if (user.username.length < 4) {
            throw MyException("用户名不能小于 4 位")
        }

        if (!user.email.isEmail()) {
            throw MyException("邮箱格式错误")
        }

        if (user.password.length < 8) {
            throw MyException("密码长度不能小于 8 位")
        }

        // 对密码生成加盐哈希
        val saltHash = hashingService.generatedSaltedHash(user.password)
        val u = user.copy(
            password = saltHash.hash,
            salt = saltHash.salt
        )
        // 添加用户
        val result = userDao.addUser(u) != null
        if (result) {
            ioScope.launch {
                // 管理员初始化完成，判断是否有文章，没有的话就插入默认文章
                if (postService.postCount() == 0L) {
                    val postRequest = firstPost()
                    postService.addPost(postRequest)
                }

                // 判断是否有菜单，没有的话就插入默认菜单
                if (menuService.menuCount() == 0L) {
                    val menuRequest = MenuRequest(
                        menuId = null,
                        displayName = "默认",
                        isMain = true
                    )
                    val menu = menuService.addMenu(menuRequest)
                    menu?.let { m ->
                        val menuItemHome = MenuItemRequest(
                            menuItemId = null,
                            displayName = "首页",
                            href = "/",
                            parentMenuId = m.menuId,
                            parentMenuItemId = null,
                            index = 0,
                            target = MenuItemTarget.SELF
                        )
                        val menuItemCategory = MenuItemRequest(
                            menuItemId = null,
                            displayName = "分类",
                            href = "/category",
                            parentMenuId = m.menuId,
                            parentMenuItemId = null,
                            index = 1,
                            target = MenuItemTarget.SELF
                        )
                        val menuItemTag = MenuItemRequest(
                            menuItemId = null,
                            displayName = "标签",
                            href = "/tag",
                            parentMenuId = m.menuId,
                            parentMenuItemId = null,
                            index = 2,
                            target = MenuItemTarget.SELF
                        )
                        val menuItemLink = MenuItemRequest(
                            menuItemId = null,
                            displayName = "友情链接",
                            href = "/link",
                            parentMenuId = m.menuId,
                            parentMenuItemId = null,
                            index = 3,
                            target = MenuItemTarget.SELF
                        )
                        val menuItemDiary = MenuItemRequest(
                            menuItemId = null,
                            displayName = "日记",
                            href = "/diary",
                            parentMenuId = m.menuId,
                            parentMenuItemId = null,
                            index = 4,
                            target = MenuItemTarget.SELF
                        )
                        menuService.addMenuItem(menuItemHome)
                        menuService.addMenuItem(menuItemCategory)
                        menuService.addMenuItem(menuItemTag)
                        menuService.addMenuItem(menuItemLink)
                        menuService.addMenuItem(menuItemDiary)
                    }
                }
            }
        }
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
     * 根据用户 ID 获取用户
     * @param userId 用户 ID
     */
    override suspend fun user(userId: Long): User? {
        return userDao.user(userId)
    }

    /**
     * 根据用户 ID 修改最后登录时间
     * @param userId 用户 ID
     */
    override suspend fun updateLastLoginTime(userId: Long): Boolean {
        return userDao.updateUserLastLoginTime(userId)
    }

    /**
     * 修改用户信息
     * @param userId 用户 ID
     * @param userInfo 用户信息
     */
    override suspend fun updateUser(userId: Long, userInfo: UserInfoRequest): Boolean {
        if (!userInfo.email.isEmail()) throw MyException("邮箱格式错误")
        if (!userInfo.username.isAlphaAndNumeric()) throw MyException("用户名只支持英文和数字")
        if (userInfo.username.length < 4) throw MyException("用户名不能小于 4 位")
        return userDao.updateUser(userId, userInfo)
    }

    /**
     * 修改用户密码
     * @param userId 用户 ID
     * @param password 新密码
     */
    override suspend fun updatePassword(userId: Long, password: String): Boolean {
        if (password.length < 8) throw MyException("密码长度不能小于 8 位")

        // 判断用户是否存在
        val user = user(userId) ?: throw MyException("用户不存在")

        // 对密码生成加盐哈希
        val saltHash = hashingService.generatedSaltedHash(password)
        return userDao.updatePassword(userId, saltHash)
    }

    /**
     * 用户登录
     * @param tokenConfig Token 令牌配置
     * @param username 用户名
     * @param password 密码
     * @param ip 请求的 IP 地址
     */
    override suspend fun login(
        tokenConfig: TokenConfig,
        username: String,
        password: String,
        ip: String
    ): AuthResponse {

        /**
         * 登录失败时记录
         */
        fun loginErrorOperate() {
            // 添加登录操作记录
            operate(
                userId = -1,
                username = username,
                desc = "用户 [${username}] 登录失败，IP [${ip}]",
                isHighRisk = false
            )
        }

        val user = user(username) ?: throw MyException("非法用户名或密码").also { loginErrorOperate() }
        // 验证密码合法性
        val isValidPassword = hashingService.verify(
            value = password,
            saltedHash = SaltedHash(
                salt = user.salt,
                hash = user.password
            )
        )

        // 密码不合法
        if (!isValidPassword) {
            loginErrorOperate()
            throw MyException("非法用户名或密码")
        }

        // 生成 Token
        val token = tokenService.generate(
            config = tokenConfig,
            userId = user.userId,
            userName = user.username
        )

        ioScope.launch {
            // 修改最后登录时间
            updateLastLoginTime(user.userId)
            // 添加登录操作记录
            operateSync(
                userId = user.userId,
                username = username,
                desc = "用户 [${user.username}] 登录成功，IP [${ip}]",
                isHighRisk = false
            )
        }

        // 封装登录响应数据类
        return AuthResponse(
            username = user.username,
            email = user.email,
            displayName = user.displayName,
            description = user.description,
            createDate = user.createDate,
            lastLoginDate = user.lastLoginDate,
            avatar = user.avatar,
            token = token
        )
    }
}