package cc.loac.security.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class HashingServiceImpl: HashingService {
    /**
     * 生成加盐哈希
     * @param value 待哈希的原始字符串数据
     * @param saltLength 盐的字节长度
     * @return 返回一个包含哈希值与盐值的 SaltedHash 对象
     */
    override fun generatedSaltedHash(value: String, saltLength: Int): SaltedHash {
        // 生成盐
        val salt = SecureRandom
            .getInstance("SHA1PRNG")
            .generateSeed(saltLength)
        // 转十六进制
        val saltAsHex = Hex.encodeHexString(salt)
        // 在数据前加上盐值
        val hash = DigestUtils.sha256Hex("$saltAsHex$value")
        return SaltedHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    /**
     * 生成不加盐哈希
     * @param value 待哈希的原始字符串数据
     * @return 返回一个包含哈希值的 String 对象
     */
    override fun generatedHash(value: String): String {
        return DigestUtils.sha256Hex(value)
    }

    /**
     * 验证哈希值是否匹配
     * @param value 待验证的原始字符串数据
     * @param saltedHash 包含已知哈希值与盐值的 SaltedHash 对象
     * @return 如果重新计算的哈希值与给定的哈希值一致，则返回 true，否则返回 false
     */
    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex("${saltedHash.salt}$value") == saltedHash.hash
    }
}