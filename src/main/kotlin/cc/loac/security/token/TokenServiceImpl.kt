package cc.loac.security.token

import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.extensions.toJSONString
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class TokenServiceImpl: TokenService {

    companion object {

        // Token 缓存 Map<用户 ID, Token>
        // 如果用户 ID 和 Token 不匹配，则触发 401
        private val tokenMap = mutableMapOf<Long, String>()
    }

    /**
     * 生成 JWT 令牌
     * @param config 令牌配置
     * @param userId 用户 ID
     * @param userName 用户名
     * @param claims Token Claim
     */
    override fun generate(
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

        return token.sign(Algorithm.HMAC256(config.secret)).also {
            // 签发 Token 后，将当前用户和签发的 Token 绑定缓存
            tokenMap[userId] = it
        }
    }

    /**
     * 验证用户 ID 和 Token 是否匹配
     * @param userId 用户 ID
     * @param token 令牌
     */
    override fun verify(userId: Long, token: String): Boolean {
        if (tokenMap.isEmpty()) return false
        return tokenMap[userId] == token
    }
}