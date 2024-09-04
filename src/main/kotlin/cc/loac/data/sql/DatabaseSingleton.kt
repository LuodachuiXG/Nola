package cc.loac.data.sql

import cc.loac.data.sql.tables.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
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
        val databaseName = config.property("ktor.storage.databaseName").getString()
        val jdbcURL = config.property("ktor.storage.jdbcURL").getString()
        val username = config.property("ktor.storage.username").getString()
        val password = config.property("ktor.storage.password").getString()
        var database = Database.connect(
            // 先不连接具体的数据库，只连接数据库服务器
            createHikariDataSource(jdbcURL.substringBefore(databaseName), driverClassName, username, password)
        )

        transaction(database) {
            if (!SchemaUtils.listDatabases().contains(databaseName)) {
                // 数据库不存在，创建数据库
                SchemaUtils.createDatabase(databaseName)
                // 连接到新创建的数据库
                database = Database.connect(
                    createHikariDataSource(jdbcURL, driverClassName, username, password)
                )
            }

            // 开启事物，要么全部成功，要么回滚
            transaction(database) {
                // 所有表
                val tables = listOf(
                    Users, Configs, Posts, Tags, Categories, PostTags,
                    PostCategories, Links, Menus, MenuItems, FileStorageModes,
                    FileGroups, Files, Diaries, AccessLogs, Comments
                )
                // 创建表
                SchemaUtils.create(*tables.toTypedArray())
            }
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