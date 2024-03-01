package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.PostDao
import cc.loac.services.CategoryService
import cc.loac.services.PostService
import cc.loac.services.TagService
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

        // 检查摘要是否为 null 并自动生成摘要
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

        // 检查摘要是否为 null 并自动生成摘要
        autoGenerateExcerpt(pr, true)
        return postDao.updatePost(pr)
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
     * 摘要如果为 null 就自动生成摘要
     * 如果当前是修改文章，则从数据库中获取文章内容
     * @param pr 文章请求数据类
     * @param isUpdate 是否是修改文章
     */
    private suspend fun autoGenerateExcerpt(pr: PostRequest, isUpdate: Boolean = false) {
        // 如果当前是修改文章，就从数据库获取文章内容
        // 否则当前是添加文章，直接总 pr 对象中获取文章内容
        val postContent = if (isUpdate) {
            postContent(pr.postId!!)?.content
        } else {
            pr.content
        }

        // 检查摘要是否为 null
        if (pr.excerpt == null) {
            if (postContent.isNullOrEmpty()) {
                // 如果内容为空，摘要则也为空
                pr.excerpt = ""
            } else {
                // 将内容转为纯文本，取前 100（如果有 100 个字的话）个字作为摘要
                pr.excerpt = with(postContent) {
                    if (this.length >= 100) {
                        markdownToPlainText().substring(0, 100)
                    } else {
                        markdownToPlainText()
                    }
                }
            }
        }
    }

}