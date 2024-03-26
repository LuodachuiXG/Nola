package cc.loac.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class JwtTokenService: TokenService {
    /**
     * 生成 JWT 令牌
     * @param config 令牌配置
     * @param claims Token Claim
     */
    override fun generate(config: TokenConfig, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claims.forEach { claim ->
            token = token.withClaim(claim.name.toString(), claim.value)
        }
        return token.sign(Algorithm.HMAC256(config.secret))
    }
}