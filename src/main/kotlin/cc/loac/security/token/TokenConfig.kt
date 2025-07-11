package cc.loac.security.token

/**
 * 令牌配置
 */
data class TokenConfig(
    /** 令牌发行者 **/
    val issuer: String,
    /** 令牌受众 **/
    val audience: String,
    /** 令牌有效期 **/
    val expiresIn: Long,
    /** 令牌 **/
    val secret: String
)
