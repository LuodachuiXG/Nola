package cc.loac.data.sql

import cc.loac.data.sql.tables.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * 数据库单例类
 * 使用 H2 数据库作为博客默认数据库
 */
object DatabaseSingleton {

    /**
     * 初始化数据库
     */
    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.storage.driverClassName").getString()
        val jdbcURL = config.property("ktor.storage.jdbcURL").getString()
        val username = config.property("ktor.storage.username").getString()
        val password = config.property("ktor.storage.password").getString()
        val database = Database.connect(
            createHikariDataSource(jdbcURL, driverClassName, username, password)
        )

        // 开启事物，要么全部成功，要么回滚
        transaction(database) {
            SchemaUtils.createDatabase("nola")
            // 用户表
            SchemaUtils.create(Users)
            // 配置表
            SchemaUtils.create(Configs)
            // 文章表
            SchemaUtils.create(Posts)
            // 标签表
            SchemaUtils.create(Tags)
            // 分类表
            SchemaUtils.create(Categories)
            // 文章标签表
            SchemaUtils.create(PostTags)
            // 文章分类表
            SchemaUtils.create(PostCategories)
            // 文章内容表
            SchemaUtils.create(PostContents)
            // 友情链接表
            SchemaUtils.create(Links)
            // 菜单表
            SchemaUtils.create(Menus)
            // 菜单项表
            SchemaUtils.create(MenuItems)
            // 文件存储方式表
            SchemaUtils.create(FileStorageModes)
            // 文件组表
            SchemaUtils.create(FileGroups)
            // 文件表
            SchemaUtils.create(Files)
            // 日记表
            SchemaUtils.create(Diaries)
        }
    }

    /**
     * 创建 Hikari 数据源
     * @param url 数据库连接地址
     * @param driver 数据库驱动类名
     * @param username 用户名
     * @param password 密码
     */
    private fun createHikariDataSource(
        url: String,
        driver: String,
        username: String,
        password: String
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        setUsername(username)
        setPassword(password)
        // 连接池最大大小
        maximumPoolSize = 3
        // 与 Exposed 默认设置同步
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}