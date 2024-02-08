package cc.loac.security.token

/**
 * 令牌声明
 */
data class TokenClaim(
    val name: String,
    val value: String
)
