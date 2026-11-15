package cc.loac.security.hashing

import org.apache.commons.codec.digest.DigestUtils
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HashingServiceTest {

    private val hashingService = HashingServiceImpl()

    @Test
    fun `bcrypt hash verifies correct password and rejects wrong password`() {
        val saltedHash = hashingService.generatedSaltedHash("correct-password", 32)

        assertTrue(saltedHash.hash.startsWith("\$2"))
        assertTrue(saltedHash.salt.isEmpty())
        assertTrue(hashingService.verify("correct-password", saltedHash))
        assertFalse(hashingService.verify("wrong-password", saltedHash))
        assertFalse(hashingService.needsRehash(saltedHash.hash))
    }

    @Test
    fun `legacy salted sha256 hash still verifies and needs rehash`() {
        val salt = "legacy-salt"
        val legacyHash = SaltedHash(
            hash = DigestUtils.sha256Hex("$salt" + "old-password"),
            salt = salt
        )

        assertTrue(hashingService.verify("old-password", legacyHash))
        assertFalse(hashingService.verify("wrong-password", legacyHash))
        assertTrue(hashingService.needsRehash(legacyHash.hash))
    }

    @Test
    fun `sha256 helper is deterministic`() {
        assertTrue(hashingService.generatedHash("nola") == hashingService.generatedHash("nola"))
        assertFalse(hashingService.generatedHash("nola") == hashingService.generatedHash("Nola"))
    }
}
