package cc.loac.security.hashing

import io.ktor.util.*

/**
 * 哈希操作接口
 */
interface HashingService {
    /**
     * 生成加盐哈希
     * @param value 待哈希的原始字符串数据
     * @param saltLength 盐的字节长度
     * @return 返回一个包含哈希值与盐值的 SaltedHash 对象
     */
    fun generatedSaltedHash(value: String, saltLength: Int = 32): SaltedHash

    /**
     * 生成不加盐哈希
     * @param value 待哈希的原始字符串数据
     * @return 返回一个包含哈希值的 String 对象
     */
    fun generatedHash(value: String): String

    /**
     * 验证哈希值是否匹配
     * @param value 待验证的原始字符串数据
     * @param saltedHash 包含已知哈希值与盐值的 SaltedHash 对象
     * @return 如果重新计算的哈希值与给定的哈希值一致，则返回 true，否则返回 false
     */
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}