package cc.loac.security.token

/**
 * 令牌操作接口
 */
interface TokenService {
    /**
     * 生成 JWT 令牌
     * @param config 令牌配置
     * @param userId 用户 ID
     * @param userName 用户名
     * @param claims Token Claim
     */
    fun generate(
        config: TokenConfig,
        userId: Long,
        userName: String,
        vararg claims: TokenClaim
    ): String

    /**
     * 验证用户 ID 和 Token 是否匹配
     * @param userId 用户 ID
     * @param token 令牌
     */
    fun verify(userId: Long, token: String): Boolean
}