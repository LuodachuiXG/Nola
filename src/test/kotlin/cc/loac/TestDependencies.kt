package cc.loac

import cc.loac.data.models.Comment
import cc.loac.data.models.Post
import cc.loac.data.sql.dao.CommentDao
import cc.loac.data.sql.dao.PostDao
import cc.loac.security.token.TokenClaim
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.lang.reflect.Proxy
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 测试使用的 Koin 容器
 * Koin 为全局单例，整个测试 JVM 只启动一次
 */
object TestKoin {
    private val started = AtomicBoolean(false)

    fun start() {
        if (!started.compareAndSet(false, true)) return
        startKoin {
            modules(
                module {
                    single<TokenService> { FakeTokenService }
                    single<CommentDao> { FakeDaos.commentDao }
                    single<PostDao> { FakeDaos.postDao }
                }
            )
        }
    }
}

/**
 * 可控的 Token 服务，仅用于路由鉴权测试
 */
object FakeTokenService : TokenService {
    @Volatile
    var validToken: String? = null

    override suspend fun generate(
        config: TokenConfig,
        userId: Long,
        userName: String,
        vararg claims: TokenClaim
    ): String = error("测试中不应生成 Token")

    override suspend fun verify(userId: Long, token: String): Boolean = token == validToken

    override suspend fun deleteToken(userId: Long): Boolean = true
}

/**
 * 评论服务测试使用的 DAO 替身
 */
object FakeDaos {
    var posts: List<Post> = emptyList()
    var comments: Map<Long, Comment> = emptyMap()
    var addedComment: Comment? = null

    fun reset() {
        posts = emptyList()
        comments = emptyMap()
        addedComment = null
    }

    val commentDao: CommentDao = fake { name, args ->
        when (name) {
            "commentById" -> comments[(args[0] as Number).toLong()]
            "addComment" -> (args[0] as Comment).also { addedComment = it }
            "comments" -> emptyList<Comment>()
            "commentCount" -> 0L
            "publicCommentCount" -> 0L
            else -> error("未预期的 CommentDao.$name 调用")
        }
    }

    val postDao: PostDao = fake { name, args ->
        when (name) {
            "posts" -> posts
            "publicPost" -> posts.firstOrNull { it.postId == (args[0] as Number).toLong() }
            else -> error("未预期的 PostDao.$name 调用")
        }
    }
}

private inline fun <reified T : Any> fake(crossinline handler: (String, Array<Any?>) -> Any?): T {
    return Proxy.newProxyInstance(T::class.java.classLoader, arrayOf(T::class.java)) { proxy, method, args ->
        when (method.name) {
            "toString" -> "Fake${T::class.simpleName}"
            "hashCode" -> System.identityHashCode(proxy)
            "equals" -> proxy === args?.firstOrNull()
            else -> handler(method.name, args ?: emptyArray())
        }
    } as T
}
