package cc.loac.services.impl

import cc.loac.data.exceptions.MyException
import cc.loac.data.models.Post
import cc.loac.data.requests.AddPostRequest
import cc.loac.data.responses.Pager
import cc.loac.data.sql.dao.CategoryDao
import cc.loac.data.sql.dao.PostDao
import cc.loac.services.CategoryService
import cc.loac.services.PostService
import cc.loac.services.TagService
import cc.loac.utils.error
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
    override suspend fun addPost(pr: AddPostRequest): Post? {
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

        // 添加文章
        return postDao.addPost(pr)
    }

    /**
     * 删除文章
     * @param postIds 文章 ID 集合
     */
    override suspend fun deletePosts(postIds: List<Int>): Boolean {
        return postDao.deletePosts(postIds)
    }

    /**
     * 修改文章
     * @param post 文章数据类
     */
    override suspend fun updatePost(post: Post): Boolean {
        return postDao.updatePost(post)
    }

    /**
     * 获取所有文章
     */
    override suspend fun posts(): List<Post> {
        return postDao.posts()
    }

    /**
     * 分页获取所有文章
     * @param page 页数
     * @param size 每页条数
     */
    override suspend fun posts(page: Int, size: Int): Pager<Post> {
        return postDao.posts(page, size)
    }
}