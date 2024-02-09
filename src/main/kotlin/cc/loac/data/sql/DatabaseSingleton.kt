package cc.loac.data.sql

import cc.loac.data.sql.tables.Configs
import cc.loac.data.sql.tables.Users
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
    fun init() {
        val driverClassName = "org.mariadb.jdbc.Driver"
        val jdbcURL = "jdbc:mariadb://localhost:3306/nola?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8"
        val user = "root"
        val password = "123456"
        val database = Database.connect(jdbcURL, driverClassName, user, password)

        // 开启事物，要么全部成功，要么回滚
        transaction(database) {
            // 创建表
            SchemaUtils.create(Users)
            SchemaUtils.create(Configs)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}