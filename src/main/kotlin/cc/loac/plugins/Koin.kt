package cc.loac.plugins

import cc.loac.data.sql.dao.*
import cc.loac.data.sql.dao.impl.*
import cc.loac.security.hashing.HashingService
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.services.*
import cc.loac.services.impl.*
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
    single<HashingService> { SHA256HashingService() }

    single<ConfigService> { ConfigServiceImpl() }
    single<ConfigDao> { ConfigDaoImpl() }

    single<UserService> { UserServiceImpl() }
    single<UserDao> { UserDaoImpl() }

    single<TagService> { TagServiceImpl() }
    single<TagDao> { TagDaoImpl() }

    single<CategoryService> { CategoryServiceImpl() }
    single<CategoryDao> { CategoryDaoImpl() }

    single<PostService> { PostServiceImpl() }
    single<PostDao> { PostDaoImpl() }

}