package cc.loac.security.token

/**
 * 令牌操作接口
 */
interface TokenService {
    /**
     * 生成 JWT 令牌
     * @param config 令牌配置
     * @param claims Token Claim
     */
    fun generate(config: TokenConfig, vararg claims: TokenClaim): String
}