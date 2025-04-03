package cc.loac.plugins

import cc.loac.data.models.enums.TokenClaimEnum
import cc.loac.security.token.TokenConfig
import cc.loac.security.token.TokenService
import cc.loac.utils.getToken
import cc.loac.utils.getTokenClaim
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.koin.java.KoinJavaComponent.inject

private val tokenService: TokenService by inject(TokenService::class.java)

/**
 * 配置 JWT 身份验证
 */
fun Application.configureSecurity(
    config: TokenConfig
) {
    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    val userId = credential.payload.getClaim(TokenClaimEnum.USER_ID.key)?.asLong() ?: return@validate null
                    val token = getToken() ?: return@validate null

                    // 验证用户 ID 和 Token 是否匹配
                    val result = tokenService.verify(
                        userId = userId,
                        token = token
                    )

                    if (!result) {
                        return@validate null
                    }

                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}