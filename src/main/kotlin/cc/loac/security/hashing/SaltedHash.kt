package cc.loac.security.hashing

/**
 * 盐、哈希数据类
 */
data class SaltedHash(
    val hash: String,
    val salt: String
)
