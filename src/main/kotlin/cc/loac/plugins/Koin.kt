package cc.loac.plugins

import cc.loac.data.sql.dao.*
import cc.loac.data.sql.dao.impl.*
import cc.loac.security.hashing.HashingService
import cc.loac.security.hashing.SHA256HashingService
import cc.loac.security.token.JwtTokenService
import cc.loac.security.token.TokenService
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
    // 令牌服务
    single<TokenService> { JwtTokenService() }
    single<HashingService> { SHA256HashingService() }

    // 配置服务
    single<ConfigService> { ConfigServiceImpl() }
    single<ConfigDao> { ConfigDaoImpl() }

    // 用户服务
    single<UserService> { UserServiceImpl() }
    single<UserDao> { UserDaoImpl() }

    // 标签服务
    single<TagService> { TagServiceImpl() }
    single<TagDao> { TagDaoImpl() }

    // 分类服务
    single<CategoryService> { CategoryServiceImpl() }
    single<CategoryDao> { CategoryDaoImpl() }

    // 文章服务
    single<PostService> { PostServiceImpl() }
    single<PostDao> { PostDaoImpl() }

    // 链接服务
    single<LinkService> { LinkServiceImpl() }
    single<LinkDao> { LinkDaoImpl() }

    // 菜单服务
    single<MenuService> { MenuServiceImpl() }
    single<MenuDao> { MenuDaoImpl() }

    // 文件服务
    single<FileService> { FileServiceImpl() }
    single<FileDao> { FileDaoImpl() }

    // 日记服务
    single<DiaryService> { DiaryServiceImpl() }
    single<DiaryDao> { DiaryDaoImpl() }

    // 访问日志服务
    single<AccessLogService> { AccessLogServiceImpl() }
    single<AccessLogDao> { AccessLogDaoImpl() }
}