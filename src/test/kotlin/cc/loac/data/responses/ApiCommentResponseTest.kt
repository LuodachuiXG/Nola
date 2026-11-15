package cc.loac.data.responses

import cc.loac.data.models.Comment
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.LinkedList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ApiCommentResponseTest {

    @Test
    fun `public comment DTO does not expose private fields`() {
        val child = Comment(
            commentId = 2,
            postId = 1,
            postTitle = "内部标题",
            parentCommentId = 1,
            content = "子评论",
            displayName = "子用户",
            email = "child@example.com",
            isPass = false
        )
        val root = Comment(
            commentId = 1,
            postId = 1,
            postTitle = "内部标题",
            content = "父评论",
            displayName = "父用户",
            email = "root@example.com",
            isPass = true,
            children = LinkedList(listOf(child))
        )

        val dto = root.toApiCommentResponse()
        val json = jacksonObjectMapper().writeValueAsString(dto)

        assertEquals(1, dto.children?.size)
        assertEquals("子评论", dto.children?.first()?.content)
        assertFalse(json.contains("\"email\""))
        assertFalse(json.contains("\"isPass\""))
        assertFalse(json.contains("\"postTitle\""))
        assertTrue(json.contains("\"displayName\":\"父用户\""))
    }
}
