package cc.loac.security.token

import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.data.redis.RedisSingleton
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

/**
 * Token 令牌服务接口实现类
 * 使用 Redis 存储 Token，同一用户仅允许一个设备登录（后登录会使前一个 Token 失效）
 */
class TokenServiceImpl: TokenService {

    companion object {
        /** Redis 中 Token 键的前缀 */
        private const val TOKEN_KEY_PREFIX = "nola:token:"
    }

    /**
     * 生成 JWT 令牌并存储到 Redis
     * @param config 令牌配置
     * @param userId 用户 ID
     * @param userName 用户名
     * @param claims Token Claim
     */
    override suspend fun generate(
        config: TokenConfig,
        userId: Long,
        userName: String,
        vararg claims: TokenClaim
    ): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))

        token.apply {
            withClaim(TokenClaimEnum.USER_ID.key, userId)
            withClaim(TokenClaimEnum.USERNAME.key, userName)
        }

        claims.forEach { claim ->
            token = token.withClaim(claim.name.toString(), claim.value)
        }

        val jwt = token.sign(Algorithm.HMAC256(config.secret))
        // 将 Token 存储到 Redis，过期时间与 JWT 一致（毫秒转秒）
        RedisSingleton.setWithExpirySync(
            key = "$TOKEN_KEY_PREFIX$userId",
            value = jwt,
            ttlSeconds = config.expiresIn / 1000
        )
        return jwt
    }

    /**
     * 验证用户 ID 和 Token 是否匹配
     * 从 Redis 中获取存储的 Token 并与请求中的 Token 比较
     * @param userId 用户 ID
     * @param token 令牌
     */
    override suspend fun verify(userId: Long, token: String): Boolean {
        val storedToken = RedisSingleton.getSync("$TOKEN_KEY_PREFIX$userId")
        return storedToken != null && storedToken == token
    }
}
