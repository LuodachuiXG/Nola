package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostDraftRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.PostDao
import cc.loac.services.CategoryService
import cc.loac.services.PostService
import cc.loac.services.TagService
import cc.loac.utils.launchCoroutine
import cc.loac.utils.markdownToPlainText
import org.koin.java.KoinJavaComponent.inject

private val postDao: PostDao by inject(PostDao::class.java)
private val tagService: TagService by inject(TagService::class.java)
private val categoryService: CategoryService by inject(CategoryService::class.java)

/**
 * 文章服务接口实现类
 */
class PostServiceImpl : PostService {

    /**
     * 添加文章
     * @param pr 添加文章请求数据类
     */
    override suspend fun addPost(pr: PostRequest): Post? {
        // 检查标签和分类是否存在
        checkTagAndCategoryExist(pr)

        // 检查是否需要自动生成摘要
        autoGenerateExcerpt(pr)

        // 添加文章
        return postDao.addPost(pr)
    }

    /**
     * 删除文章
     * 只能删除处于回收（已删除）状态的文章
     * @param postIds 文章 ID 集合
     */
    override suspend fun deletePosts(postIds: List<Int>): Boolean {
        // 先判断给定的文章是否都处于回收状态
        val posts = posts(postIds, false)
        if (posts.any { it.status != PostStatus.DELETED }) {
            throw MyException("只能删除处于回收站的文章")
        }
        return postDao.deletePosts(postIds)
    }

    /**
     * 修改文章为删除状态
     * @param postIds 文章 ID 集合
     */
    override suspend fun updatePostStatusToDeleted(postIds: List<Int>): Boolean {
        return postDao.updatePostStatusToDeleted(postIds)
    }

    /**
     * 修改文章
     * @param pr 文章请求数据类
     */
    override suspend fun updatePost(pr: PostRequest): Boolean {
        // 检查标签和分类是否存在
        checkTagAndCategoryExist(pr)

        // 检查是否需要自动生成摘要
        autoGenerateExcerpt(pr, true)
        return postDao.updatePost(pr)
    }

    /**
     * 修改文章摘要
     * @param postId 文章 ID
     * @param excerpt 摘要
     */
    override suspend fun updatePostExcerpt(postId: Int, excerpt: String): Boolean {
        return postDao.updatePostExcerpt(postId, excerpt)
    }

    /**
     * 尝试通过文章正文修改文章摘要
     * @param postId 文章 ID
     */
    override suspend fun tryUpdatePostExcerptByPostContent(postId: Int): Boolean {
        // 获取到文章
        posts(listOf(postId), false).firstOrNull()?.let { post ->
            // 判断是否需要自动生成摘要
            if (post.autoGenerateExcerpt) {
                // 当前文章需要自动生成摘要
                // 获取到文章正文内容
                val postContent = postContent(postId)
                // 根据文章正文内容生成摘要
                val e = generateExcerptByString(postContent?.content)
                // 更新文章摘要
                return updatePostExcerpt(postId, e)
            }
        }
        return false
    }

    /**
     * 获取所有文章
     */
    override suspend fun posts(): List<Post> {
        return postDao.posts()
    }

    /**
     * 根据文章 ID 获取文章
     * @param postIds 文章 ID 集合
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    override suspend fun posts(postIds: List<Int>, includeTagAndCategory: Boolean): List<Post> {
        return postDao.posts(postIds, includeTagAndCategory)
    }

    /**
     * 分页获取所有文章
     * @param page 页数
     * @param size 每页条数
     */
    override suspend fun posts(
        page: Int,
        size: Int,
        status: PostStatus?,
        visible: PostVisible?,
        key: String?,
        tag: Int?,
        category: Int?,
        sort: PostSort?
    ): Pager<Post> {
        return postDao.posts(page, size, status, visible, key, tag, category, sort)
    }

    /**
     * 根据文章别名获取文章
     * @param slug 文章别名
     */
    override suspend fun postBySlug(slug: String): Post? {
        return postDao.postBySlug(slug)
    }

    /**
     * 根据关键字获取文章
     * 关键字：文章标题、别名、摘要、内容
     * @param key 关键字
     */
    override suspend fun postsByKey(key: String): List<Post> {
        return postDao.postsByKey(key)
    }

    /**
     * 获取文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftName 草稿名
     */
    override suspend fun postContent(
        postId: Int,
        status: PostContentStatus,
        draftName: String?
    ): PostContent? {
        return postDao.postContent(postId, status, draftName)
    }

    /**
     * 添加文章草稿
     * @param postId 文章 ID
     * @param content 文章内容
     * @param draftName 草稿名
     */
    override suspend fun addPostDraft(postId: Int, content: String, draftName: String): PostContent? {
        // 先判断文章是否存在
        if (!isPostExist(postId)) throw MyException("文章 [$postId] 不存在")
        // 判断草稿名是否已经存在
        if (isPostDraftNameExist(postId, draftName)) throw MyException("草稿名 [$draftName] 已经存在")
        return postDao.addPostDraft(postId, content, draftName)
    }

    /**
     * 删除文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftNames 草稿名集合
     */
    override suspend fun deletePostContent(postId: Int, status: PostContentStatus, draftNames: List<String>?): Boolean {
        return postDao.deletePostContent(postId, status, draftNames)
    }

    /**
     * 修改文章内容
     * @param postContent 文章内容请求数据类
     */
    override suspend fun updatePostContent(
        postContent: PostContentRequest,
        status: PostContentStatus,
        draftName: String?
    ): Boolean {
        val result = postDao.updatePostContent(postContent, status, draftName)

        // 启动线程执行耗时操作
        launchCoroutine {
            // 如果修改文章没有任何操作，就不执行下面的剩余操作
            if (!result) return@launchCoroutine

            // 如果修改的是正文内容
            if (status == PostContentStatus.PUBLISHED) {
                // 尝试通过文章正文更新文章摘要
                tryUpdatePostExcerptByPostContent(postContent.postId)
            }
        }
        return result
    }

    /**
     * 修改文章草稿名
     * @param postId 文章 ID
     * @param oldName 老草稿名
     * @param newName 新草稿名
     */
    override suspend fun updatePostDraftName(postId: Int, oldName: String, newName: String): Boolean {
        // 先判断新的草稿名是否已经存在
        if (isPostDraftNameExist(postId, newName)) throw MyException("草稿名 [$newName] 已存在")
        return postDao.updatePostDraftName(postId, oldName, newName)
    }

    /**
     * 将文章草稿转换为文章正文
     * @param postId 文章 ID
     * @param draftName 草稿名
     * @param deleteContent 是否删除原来的正文
     * @param contentName 文章正文名，留空将默认使用被转换为正文的旧草稿名。
     */
    override suspend fun updatePostDraft2Content(
        postId: Int,
        draftName: String,
        deleteContent: Boolean,
        contentName: String?
    ): Boolean {
        // 先判断文章是否存在
        if (!isPostExist(postId)) throw MyException("文章 [$postId] 不存在")
        // 判断草稿名是否存在
        if (!isPostDraftNameExist(postId, draftName)) throw MyException("草稿名 [$draftName] 不存在")
        val result = postDao.updatePostDraft2Content(postId, draftName, deleteContent, contentName)
        // 是否修改成功
        if (result) {
            // 启动线程执行耗时操作
            launchCoroutine {
                // 尝试根据新的文章正文更新文章摘要
                tryUpdatePostExcerptByPostContent(postId)
                // 修改文章最后修改时间
                postDao.updatePostLastModifyTime(postId)
            }
        }
        return result
    }

    /**
     * 检查标签和分类是否存在
     * @param pr 文章请求数据类
     */
    private suspend fun checkTagAndCategoryExist(pr: PostRequest) {
        // 检查传来的标签 ID 是否都存在
        if (!pr.tagIds.isNullOrEmpty()) {
            val nonExistentIds = tagService.isIdsExist(pr.tagIds)
            if (nonExistentIds.isNotEmpty()) {
                throw MyException("标签 $nonExistentIds 不存在")
            }
        }

        // 检查传过来的分类 ID 是否存在
        if (pr.categoryId != null) {
            categoryService.category(pr.categoryId) ?: throw MyException("分类 ${pr.categoryId} 不存在")
        }
    }

    /**
     * 自动生成摘要
     * 如果 autoGenerateExcerpt 为 true 就自动生成摘要
     * 如果当前是修改文章，则从数据库中获取文章内容
     * @param pr 文章请求数据类
     * @param isUpdate 是否是修改文章
     */
    private suspend fun autoGenerateExcerpt(pr: PostRequest, isUpdate: Boolean = false) {
        // 不用自动生成摘要
        if (!pr.autoGenerateExcerpt) return


        // 如果当前是修改文章，就从数据库获取文章内容
        // 否则当前是添加文章，直接总 pr 对象中获取文章内容
        val postContent = if (isUpdate) {
            postContent(pr.postId!!)?.content
        } else {
            pr.content
        }

        if (postContent.isNullOrEmpty()) {
            // 如果内容为空，摘要则也为空
            pr.excerpt = ""
        } else {
            // 生成摘要
            pr.excerpt = generateExcerptByString(postContent)
        }
    }

    /**
     * 根据一段 Markdown / PlainText 生成摘要
     * @param str 内容
     * @param length 摘要长度
     */
    private fun generateExcerptByString(str: String?, length: Int = 100): String {
        if (str.isNullOrEmpty()) return ""
        var excerpt = str.markdownToPlainText()
        if (excerpt.length >= length) {
            excerpt = excerpt.substring(0, length)
        }
        return excerpt
    }

    /**
     * 判断文章是否存在
     * @param postId 文章 ID
     */
    private suspend fun isPostExist(postId: Int): Boolean {
        return posts(listOf(postId)).firstOrNull() != null
    }

    /**
     * 判断草稿名是否存在
     * @param postId 文章 ID
     * @param draftName 草稿名
     */
    private suspend fun isPostDraftNameExist(postId: Int, draftName: String): Boolean {
        return postContent(postId, PostContentStatus.DRAFT, draftName) != null
    }

}