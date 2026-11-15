package cc.loac.security.hashing

import at.favre.lib.crypto.bcrypt.BCrypt
import org.apache.commons.codec.digest.DigestUtils

class HashingServiceImpl : HashingService {

    companion object {
        private const val BCRYPT_COST = 12
    }

    /**
     * 生成 BCrypt 哈希
     * @param value 待哈希的原始字符串数据
     * @param saltLength 保留参数以兼容旧接口，BCrypt 自带盐值
     * @return 返回一个包含哈希值与空盐值的 SaltedHash 对象
     */
    override fun generatedSaltedHash(value: String, saltLength: Int): SaltedHash {
        val hash = BCrypt.withDefaults().hashToString(BCRYPT_COST, value.toCharArray())
        return SaltedHash(
            hash = hash,
            salt = ""
        )
    }

    /**
     * 生成 SHA-256 哈希（保留用于非密码场景和历史兼容）
     * @param value 待哈希的原始字符串数据
     * @return 返回一个包含哈希值的 String 对象
     */
    override fun generatedHash(value: String): String {
        return DigestUtils.sha256Hex(value)
    }

    /**
     * 验证哈希值是否匹配，兼容旧的加盐 SHA-256 与新 BCrypt
     * @param value 待验证的原始字符串数据
     * @param saltedHash 包含已知哈希值与盐值的 SaltedHash 对象
     * @return 如果哈希值一致，则返回 true，否则返回 false
     */
    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        val hash = saltedHash.hash
        if (isBcryptHash(hash)) {
            return BCrypt.verifyer()
                .verify(value.toCharArray(), hash.toCharArray())
                .verified
        }
        return DigestUtils.sha256Hex("${saltedHash.salt}$value") == hash
    }

    /**
     * 判断哈希是否需要升级为 BCrypt
     * @param hash 已存储的哈希值
     */
    override fun needsRehash(hash: String): Boolean = !isBcryptHash(hash)

    /**
     * 判断是否为 BCrypt 哈希
     */
    private fun isBcryptHash(hash: String): Boolean {
        return hash.startsWith("\$2a\$") || hash.startsWith("\$2b\$") || hash.startsWith("\$2y\$")
    }
}
