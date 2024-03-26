package cc.loac.security.token

import cc.loac.data.models.enums.TokenClaimEnum

/**
 * 令牌声明
 */
data class TokenClaim(
    val name: TokenClaimEnum,
    val value: String
)
