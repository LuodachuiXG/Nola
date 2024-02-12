package cc.loac.plugins

import cc.loac.data.sql.dao.ConfigDao
import cc.loac.data.sql.dao.UserDao
import cc.loac.data.sql.dao.impl.ConfigDaoImpl
import cc.loac.data.sql.dao.impl.UserDaoImpl
import cc.loac.services.ConfigService
import cc.loac.services.UserService
import cc.loac.services.impl.ConfigServiceImpl
import cc.loac.services.impl.UserServiceImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * 配置 Koin
 * 依赖注入插件
 */
fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

/** 配置 Koin 注入模块 **/
val appModule = module {
    single<ConfigService> { ConfigServiceImpl() }
    single<ConfigDao> { ConfigDaoImpl() }

    single<UserService> { UserServiceImpl() }
    single<UserDao> { UserDaoImpl() }
}