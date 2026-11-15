package cc.loac.services

import cc.loac.FakeDaos
import cc.loac.TestKoin
import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Comment
import cc.loac.data.models.Post
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.services.impl.CommentServiceImpl
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CommentServiceTest {

    private val commentService = CommentServiceImpl()

    @BeforeTest
    fun setup() {
        TestKoin.start()
        FakeDaos.reset()
    }

    @Test
    fun `parent comment from another post is rejected`() = runBlocking {
        FakeDaos.posts = listOf(testPost(1))
        FakeDaos.comments = mapOf(10L to testComment(id = 10, postId = 99))

        val error = assertFailsWith<MyException> {
            commentService.addComment(
                testComment(postId = 1, parentCommentId = 10),
                isApiRequest = false
            )
        }

        assertTrue(error.message.orEmpty().contains("父评论"))
    }

    @Test
    fun `reply comment with mismatched parent is rejected`() = runBlocking {
        FakeDaos.posts = listOf(testPost(1))
        FakeDaos.comments = mapOf(
            10L to testComment(id = 10, postId = 1),
            11L to testComment(id = 11, postId = 1, parentCommentId = 20)
        )

        val error = assertFailsWith<MyException> {
            commentService.addComment(
                testComment(postId = 1, parentCommentId = 10, replyCommentId = 11),
                isApiRequest = false
            )
        }

        assertTrue(error.message.orEmpty().contains("回复的评论"))
    }

    @Test
    fun `reply without parent is rejected`() = runBlocking {
        FakeDaos.posts = listOf(testPost(1))

        val error = assertFailsWith<MyException> {
            commentService.addComment(
                testComment(postId = 1, replyCommentId = 11),
                isApiRequest = false
            )
        }

        assertTrue(error.message.orEmpty().contains("未指定父评论"))
    }

    @Test
    fun `valid reply inherits display name`() = runBlocking {
        FakeDaos.posts = listOf(testPost(1))
        FakeDaos.comments = mapOf(
            10L to testComment(id = 10, postId = 1),
            11L to testComment(id = 11, postId = 1, parentCommentId = 10, displayName = "被回复用户")
        )

        val result = commentService.addComment(
            testComment(postId = 1, parentCommentId = 10, replyCommentId = 11),
            isApiRequest = false
        )

        assertNotNull(result)
        assertEquals("被回复用户", FakeDaos.addedComment?.replyDisplayName)
    }

    private fun testPost(id: Long) = Post(
        postId = id,
        title = "测试文章",
        autoGenerateExcerpt = false,
        excerpt = "",
        slug = "post-$id",
        cover = null,
        allowComment = true,
        pinned = false,
        status = PostStatus.PUBLISHED,
        visible = PostVisible.VISIBLE,
        encrypted = false,
        password = null,
        visit = 0,
        category = null,
        tags = emptyList(),
        createTime = 0,
        lastModifyTime = null
    )

    private fun testComment(
        id: Long = -1,
        postId: Long,
        parentCommentId: Long? = null,
        replyCommentId: Long? = null,
        displayName: String = "测试用户"
    ) = Comment(
        commentId = id,
        postId = postId,
        parentCommentId = parentCommentId,
        replyCommentId = replyCommentId,
        content = "测试评论",
        displayName = displayName,
        email = "test@example.com"
    )
}
