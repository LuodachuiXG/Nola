package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.requests.PostStatusRequest
import cc.loac.data.requests.newPostRequestByNameAndContent
import cc.loac.data.responses.*
import cc.loac.data.sql.dao.PostDao
import cc.loac.services.CategoryService
import cc.loac.services.PostService
import cc.loac.services.TagService
import cc.loac.utils.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
import java.io.File
import java.util.Date

/**
 * 文章服务接口实现类
 */
class PostServiceImpl : PostService {

    private val postDao: PostDao by inject(PostDao::class.java)
    private val tagService: TagService by inject(TagService::class.java)
    private val categoryService: CategoryService by inject(CategoryService::class.java)

    /**
     * 添加文章
     * @param pr 添加文章请求数据类
     */
    override suspend fun addPost(pr: PostRequest): Post? {
        // 检查别名是否重复
        postBySlug(pr.slug)?.let { throw MyException("别名 [${pr.slug}] 已存在") }

        // 检查标签和分类是否存在
        checkTagAndCategoryExist(pr)

        // 检查是否需要自动生成摘要
        autoGenerateExcerpt(pr)

        // 添加文章
        return postDao.addPost(pr)
    }

    /**
     * 根据名称和内容批量添加文章
     * @param names 文章名称集合
     * @param contents 文章内容集合
     * @param errorMsg 文章添加错误回调
     * @return 添加成功的文章集合
     */
    override suspend fun addPost(
        names: List<String>,
        contents: List<String>,
        errorMsg: (name: String, error: String) -> Unit
    ): List<Post> {
        val result = mutableListOf<Post>()
        names.forEachIndexed { i, name ->
            // 封装文章请求类，用于添加文章
            val pr = newPostRequestByNameAndContent(name, contents[i])
            try {
                // 检查别名是否重复
                postBySlug(pr.slug)?.let {
                    // 别名重复，修改别名。在别名后面加 _随机六位字符
                    pr.slug = "${pr.slug}_${randomString(6)}"
                }
                // 检查是否需要自动生成摘要
                autoGenerateExcerpt(pr)

                // 添加文章
                postDao.addPost(pr)?.let {
                    result.add(it)
                }
            } catch (e: Exception) {
                errorMsg(name, e.message ?: "未知错误，请查看服务器")
            }
        }
        return result
    }

    /**
     * 删除文章
     * 只能删除处于回收（已删除）状态的文章
     * @param postIds 文章 ID 集合
     */
    override suspend fun deletePosts(postIds: List<Long>): Boolean {
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
    override suspend fun updatePostStatusToDeleted(postIds: List<Long>): Boolean {
        return postDao.updatePostStatusToDeleted(postIds)
    }

    /**
     * 将文章转为指定状态
     * @param postIds 文章 ID 集合
     * @param status 文章状态
     */
    override suspend fun updatePostStatusTo(postIds: List<Long>, status: PostStatus): Boolean {
        return postDao.updatePostStatusTo(postIds, status)
    }

    /**
     * 修改文章
     * @param pr 文章请求数据类
     */
    override suspend fun updatePost(pr: PostRequest): Boolean {
        // 检查别名是否重复
        postBySlug(pr.slug)?.let {
            // 别名重复，且不是相同文章，别名重复
            if (it.postId != pr.postId) {
                throw MyException("别名 [${pr.slug}] 已存在")
            }
        }
        // 检查标签和分类是否存在
        checkTagAndCategoryExist(pr)
        // 检查是否需要自动生成摘要
        autoGenerateExcerpt(pr, true)
        return postDao.updatePost(pr)
    }

    /**
     * 修改文章状态
     * 文章状态、可见性、置顶
     * @param postStatusRequest 文章状态请求数据类
     */
    override suspend fun updatePostStatus(postStatusRequest: PostStatusRequest): Boolean {
        if (postStatusRequest.status == null &&
            postStatusRequest.visible == null &&
            postStatusRequest.pinned == null
        ) {
            return false
        }
        return postDao.updatePostStatus(postStatusRequest)
    }

    /**
     * 修改文章摘要
     * @param postId 文章 ID
     * @param excerpt 摘要
     */
    override suspend fun updatePostExcerpt(postId: Long, excerpt: String): Boolean {
        return postDao.updatePostExcerpt(postId, excerpt)
    }

    /**
     * 尝试通过文章正文修改文章摘要
     * @param postId 文章 ID
     */
    override suspend fun tryUpdatePostExcerptByPostContent(postId: Long): Boolean {
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
     * 增加文章访问量
     * @param postId 文章 ID
     */
    override suspend fun addPostVisit(postId: Long): Boolean {
        return postDao.addPostVisit(postId)
    }

    /**
     * 获取文章总数
     */
    override suspend fun postCount(): Long {
        return postDao.postCount();
    }

    /**
     * 获取所有文章
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    override suspend fun posts(includeTagAndCategory: Boolean): List<Post> {
        return postDao.posts(includeTagAndCategory)
    }

    /**
     * 根据文章 ID 获取文章
     * @param postIds 文章 ID 集合
     * @param includeTagAndCategory 包含标签和分类（耗时操作，非必要不包含）
     */
    override suspend fun posts(postIds: List<Long>, includeTagAndCategory: Boolean): List<Post> {
        return postDao.posts(postIds, includeTagAndCategory)
    }

    /**
     * 分页获取所有文章
     * @param page 当前页数
     * @param size 每页条数
     * @param status 文章状态
     * @param visible 文章可见性
     * @param key 关键字
     * @param tagId 文章标签 ID
     * @param categoryId 文章分类 ID
     * @param sort 文章排序
     */
    override suspend fun posts(
        page: Int,
        size: Int,
        status: PostStatus?,
        visible: PostVisible?,
        key: String?,
        tagId: Long?,
        categoryId: Long?,
        sort: PostSort?
    ): Pager<Post> {
        return postDao.posts(page, size, status, visible, key, tagId, categoryId, sort)
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
     * 获取文章 API 接口
     * @param page 当前页数
     * @param size 每页条数
     * @param key 关键字
     * @param tagId 文章标签 ID
     * @param categoryId 文章分类 ID
     * @param tag 标签名或别名
     * @param category 分类名或别名
     */
    override suspend fun apiPosts(
        page: Int,
        size: Int,
        key: String?,
        tagId: Long?,
        categoryId: Long?,
        tag: String?,
        category: String?
    ): Pager<ApiPostResponse> {
        return postDao.apiPosts(page, size, key, tagId, categoryId, tag, category)
    }

    /**
     * 获取文章所有内容
     * 包括文章正文和文章所有草稿
     * @param postId 文章 ID
     */
    override suspend fun postContents(postId: Long): List<PostContentResponse> {
        // 判断文章是否存在
        if (!isPostExist(postId)) throw MyException("文章 [$postId] 不存在")
        return postDao.postContents(postId)
    }

    /**
     * 获取文章内容
     * @param postId 文章 ID
     * @param status 文章内容状态
     * @param draftName 草稿名
     */
    override suspend fun postContent(
        postId: Long,
        status: PostContentStatus,
        draftName: String?
    ): PostContent? {
        return postDao.postContent(postId, status, draftName)
    }

    /**
     * 获取文章博客 API 接口
     * ID 和别名至少存在一个
     * @param postId 文章 ID
     * @param slug 文章别名
     * @param password 密码
     */
    override suspend fun apiPostContent(postId: Long?, slug: String?, password: String?): ApiPostContentResponse? {
        if (postId == null && slug == null) return null
        // 如果文章 ID 不为空，通过文章 ID 获取文章
        val post = if (postId != null) {
            posts(listOf(postId), true).firstOrNull() ?: return null
        } else {
            // 通过文章别名获取文章
            postBySlug(slug!!) ?: return null
        }

        // 博客前端 API 只能获取已发布文章内容
        if (post.status != PostStatus.PUBLISHED) return null

        // 判断文章是否有密码，以及密码是否正确
        if (post.encrypted) {
            // 文章有密码，但是接口提供的密码为空
            password ?: return null
            // 密码不正确
            if (!isPostPasswordValid(post.postId, password)) {
                throw MyException("文章密码不正确")
            }
        }

        // 获取文章正文
        val postContent = postContent(post.postId, PostContentStatus.PUBLISHED) ?: return null

        launchIO {
            // 文章浏览量加一
            addPostVisit(post.postId)
        }

        // 封装博客 API 文章内容响应类
        return ApiPostContentResponse(
            post = ApiPostResponse(
                postId = post.postId,
                title = post.title,
                excerpt = post.excerpt,
                slug = post.slug,
                cover = post.cover,
                allowComment = post.allowComment,
                pinned = post.pinned,
                encrypted = post.encrypted,
                visit = post.visit,
                category = post.category,
                tags = post.tags,
                createTime = post.createTime,
                lastModifyTime = post.lastModifyTime
            ),
            content = postContent.content
        )
    }

    /**
     * 添加文章草稿
     * @param postId 文章 ID
     * @param content 文章内容
     * @param draftName 草稿名
     */
    override suspend fun addPostDraft(postId: Long, content: String, draftName: String): PostContent? {
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
    override suspend fun deletePostContent(postId: Long, status: PostContentStatus, draftNames: List<String>?): Boolean {
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
        launchIO {
            // 如果修改文章没有任何操作，就不执行下面的剩余操作
            if (!result) return@launchIO

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
    override suspend fun updatePostDraftName(postId: Long, oldName: String, newName: String): Boolean {
        if (oldName == newName) return false
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
        postId: Long,
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
            launchIO {
                // 尝试根据新的文章正文更新文章摘要
                tryUpdatePostExcerptByPostContent(postId)
                // 修改文章最后修改时间
                postDao.updatePostLastModifyTime(postId)
            }
        }
        return result
    }

    /**
     * 验证文章密码是否正确
     * @param postId 文章 ID
     * @param password 密码
     */
    override suspend fun isPostPasswordValid(postId: Long, password: String): Boolean {
        return postDao.isPostPasswordValid(postId, password)
    }

    /**
     * 导出所有文章
     */
    override suspend fun exportPosts(): ExportPostResponse {
        // 总处理文章数量
        var totalCount = 0
        // 成功的文章数量
        var successCount = 0
        // 失败原因的数组
        val failResult = mutableListOf<String>()
        val scope = CoroutineScope(Dispatchers.IO)

        // 文件夹名前缀
        val filePrefix = "${Date().formatDate()}_Post"

        // 临时文件夹地址，一般以当前时间命名
        val tempDir = File("./.nola/temp/$filePrefix")

        if (tempDir.exists()) tempDir.deleteRecursively()

        // 先获取所有未删除的文章
        val posts = posts(false).filter {
            it.status != PostStatus.DELETED
        }

        // 启用多协程获取所有文章的内容，并等待所有协程都执行完毕
        val jobs = mutableListOf<Job>()
        posts.forEach { post ->
            // 每个文章启动一个协程
            jobs += scope.launch {
                // 当前文章的所有内容列表（包括正文和草稿）这里不是真实的文章内容
                val postContentItems = postContents(post.postId)
                // 获取文章的正文和所有草稿的实际内容，并写到文件
                postContentItems.forEach { contentItem ->
                    totalCount++
                    // 获取当前文章的具体内容
                    val content = postContent(contentItem.postId, contentItem.status, contentItem.draftName)
                    if (content == null) {
                        failResult += if (contentItem.status == PostContentStatus.PUBLISHED) {
                            "[${post.title}] 文章没有任何内容"
                        } else {
                            "[${post.title}] 文章的 [${contentItem.draftName}] 草稿没有任何内容"
                        }
                        return@launch
                    }
                    // 将当前文章内容写到临时文件夹
                    postToTempDir(post, content, tempDir) { errMsg ->
                        // 写到文件出错回调
                        // 将错误原因写到数组
                        failResult += errMsg
                    }.let {
                        if (it) {
                            // 文章内容写到文件成功
                            successCount++
                        }
                    }
                }
            }
        }


        // 等待所有协程执行完毕
        jobs.joinAll()

        val backupFile = File("./.nola/backup")
        if (!backupFile.exists()) {
            backupFile.mkdirs()
        }
        // 将存储文章内容的临时文件夹压缩
        tempDir.createZip("./.nola/backup/${filePrefix}.zip") ?: throw MyException("压缩文件失败")
        // 删除临时文件夹
        tempDir.deleteRecursively()
        return ExportPostResponse(
            successCount = successCount,
            failCount = failResult.size,
            failResult = failResult,
            path = "/backup/${filePrefix}.zip",
            count = totalCount,
        )
    }


    /**
     * 将文章内容写入临时文件夹
     * @param post 文章数据类
     * @param postContent 文章内容数据类
     * @param dir 要写入的临时文件夹
     * @param onFail 失败时的回调函数
     */
    private fun postToTempDir(
        post: Post,
        postContent: PostContent,
        dir: File,
        onFail: (String) -> Unit = {}
    ): Boolean {
        if (!dir.exists()) {
            dir.mkdirs()
        } else if (!dir.isDirectory) {
            onFail("[${post.title}] 写入临时文件夹失败，因为临时文件夹地址不是一个文件夹")
            return false
        }

        var postFileName = post.title
        if (postContent.status == PostContentStatus.DRAFT) {
            postFileName += "_${postContent.draftName}"
        }
        val postFile = File(dir, "$postFileName.md")
        try {
            postFile.writeText(postContent.content)
            return true;
        } catch (e: Exception) {
            onFail("[${post.title}] 写入临时文件夹失败，${e.message}")
            return false
        }
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
        // 否则当前是添加文章，直接从 pr 对象中获取文章内容
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
    private suspend fun isPostExist(postId: Long): Boolean {
        return posts(listOf(postId)).firstOrNull() != null
    }

    /**
     * 判断草稿名是否存在
     * @param postId 文章 ID
     * @param draftName 草稿名
     */
    private suspend fun isPostDraftNameExist(postId: Long, draftName: String): Boolean {
        return postContent(postId, PostContentStatus.DRAFT, draftName) != null
    }

}