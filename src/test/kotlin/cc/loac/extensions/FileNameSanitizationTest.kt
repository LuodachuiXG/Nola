package cc.loac.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FileNameSanitizationTest {

    @Test
    fun `path traversal characters are removed`() {
        val sanitized = "../../etc/passwd".sanitizeFileName()

        assertFalse(sanitized.contains('/'))
        assertFalse(sanitized.contains('\\'))
        assertFalse(sanitized.contains(".."))
    }

    @Test
    fun `windows separators and control characters are removed`() {
        val sanitized = "..\\..\\windows\u0000\\system32".sanitizeFileName()

        assertFalse(sanitized.contains('/'))
        assertFalse(sanitized.contains('\\'))
        assertFalse(sanitized.contains(".."))
        assertFalse(sanitized.contains("\u0000"))
    }

    @Test
    fun `blank name falls back to unnamed`() {
        assertEquals("unnamed", "   ".sanitizeFileName())
    }

    @Test
    fun `normal file name is preserved`() {
        assertEquals("nola-post.md", "nola-post.md".sanitizeFileName())
        assertTrue("nola-post.md".sanitizeFileName().endsWith(".md"))
    }

    @Test
    fun `backup file name component removes paths and never escapes`() {
        val sanitized = "../../other/post".sanitizeBackupFileName("post")

        assertFalse(sanitized.contains('/'))
        assertFalse(sanitized.contains('\\'))
        assertFalse(sanitized.contains(".."))
        assertEquals(80, "a".repeat(100).sanitizeBackupFileName("post").length)
    }

    @Test
    fun `blank backup name component falls back`() {
        assertEquals("draft", "...".sanitizeBackupFileName("draft"))
    }
}
