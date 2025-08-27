package cc.loac.services.impl

import cc.loac.data.models.Category
import cc.loac.data.models.Operation
import cc.loac.data.models.Post
import cc.loac.data.models.Tag
import cc.loac.data.responses.ApiOverviewCount
import cc.loac.data.responses.ApiOverviewResponse
import cc.loac.data.responses.OverviewCount
import cc.loac.data.responses.OverviewResponse
import cc.loac.data.responses.toApiPostResponse
import cc.loac.manager.BlogOnlineManager
import cc.loac.services.*
import com.google.protobuf.Api
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject

/**
 * 概览服务接口实现类
 */
class OverviewServiceImpl : OverviewService {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val postService: PostService by inject(PostService::class.java)
    private val tagService: TagService by inject(TagService::class.java)
    private val categoryService: CategoryService by inject(CategoryService::class.java)
    private val commentService: CommentService by inject(CommentService::class.java)
    private val diaryService: DiaryService by inject(DiaryService::class.java)
    private val fileService: FileService by inject(FileService::class.java)
    private val linkService: LinkService by inject(LinkService::class.java)
    private val menuService: MenuService by inject(MenuService::class.java)
    private val operationService: OperationService by inject(OperationService::class.java)
    private val userService: UserService by inject(UserService::class.java)
    private val configService: ConfigService by inject(ConfigService::class.java)

    /**
     * 获取概览信息
     * @param userId 用户ID
     */
    override suspend fun getOverview(userId: Long): OverviewResponse = coroutineScope {
        // 统计数量
        val countJob = listOf(
            ioScope.async { postService.postCount() },
            ioScope.async { commentService.commentCount() },
            ioScope.async { diaryService.diaryCount() },
            ioScope.async { fileService.fileCount() },
            ioScope.async { linkService.linkCount() },
            ioScope.async { menuService.mainMenuItemCount() },
            // 最后登录时间
            ioScope.async { userService.lastLoginDate(id = userId) },
            // 博客创建时间
            ioScope.async { configService.blogInfo()?.createDate },
            // 文章总浏览量
            ioScope.async { postService.postVisitCount() }
        )

        // 文章最多的 6 个标签
        val tagJob = ioScope.async {
            tagService.topTags()
        }

        // 文章最多的 6 个分类
        val categoryJob = ioScope.async {
            categoryService.topCategories()
        }

        // 浏览量最多的文章
        val mostViewedPostJob = ioScope.async {
            postService.mostViewedPost()
        }

        // 最后一条操作信息
        val lastOperationJob = ioScope.async {
            operationService.lastOperation()
        }

        val countResult = countJob.awaitAll()
        val otherResult = listOf(tagJob, categoryJob, mostViewedPostJob, lastOperationJob).awaitAll()

        val tags = (otherResult[0] as? List<*>)?.filterIsInstance<Tag>() ?: emptyList()
        val categories = (otherResult[1] as? List<*>)?.filterIsInstance<Category>() ?: emptyList()
        val mostViewedPost = otherResult[2] as Post?
        val lastOperation = otherResult[3] as Operation?

        // 概览项目数量
        val overviewCount = OverviewCount(
            post = countResult[0] ?: 0L,
            tag = tags.size.toLong(),
            category = categories.size.toLong(),
            comment = countResult[1] ?: 0L,
            diary = countResult[2] ?: 0L,
            file = countResult[3] ?: 0L,
            link = countResult[4] ?: 0L,
            menu = countResult[5] ?: 0L
        )

        OverviewResponse(
            postVisitCount = countResult[8] ?: 0L,
            count = overviewCount,
            tags = tags,
            categories = categories,
            mostViewedPost = mostViewedPost,
            lastOperation = lastOperation?.operationDesc,
            lastLoginDate = countResult[6],
            createDate = countResult[7]
        )
    }

    /**
     * 获取博客 API 概览信息
     */
    override suspend fun getApiOverview(): ApiOverviewResponse {
        // 统计数量
        val countJob = listOf(
            ioScope.async { postService.postCount() },
            ioScope.async { commentService.commentCount() },
            ioScope.async { diaryService.diaryCount() },
            ioScope.async { linkService.linkCount() },
            // 博客创建时间
            ioScope.async { configService.blogInfo()?.createDate },
            // 文章总浏览量
            ioScope.async { postService.postVisitCount() }
        )

        // 文章最多的 6 个标签
        val tagJob = ioScope.async {
            tagService.topTags()
        }

        // 文章最多的 6 个分类
        val categoryJob = ioScope.async {
            categoryService.topCategories()
        }

        // 浏览量最多的文章
        val mostViewedPostJob = ioScope.async {
            postService.mostViewedPost()
        }
        val countResult = countJob.awaitAll()
        val otherResult = listOf(tagJob, categoryJob, mostViewedPostJob).awaitAll()

        val tags = (otherResult[0] as? List<*>)?.filterIsInstance<Tag>() ?: emptyList()
        val categories = (otherResult[1] as? List<*>)?.filterIsInstance<Category>() ?: emptyList()
        val mostViewedPost = otherResult[2] as Post?

        // 概览项目数量
        val overviewCount = ApiOverviewCount(
            post = countResult[0] ?: 0L,
            tag = tags.size.toLong(),
            category = categories.size.toLong(),
            comment = countResult[1] ?: 0L,
            diary = countResult[2] ?: 0L,
            link = countResult[3] ?: 0L,
        )

        return ApiOverviewResponse(
            postVisitCount = countResult[5] ?: 0L,
            count = overviewCount,
            tags = tags,
            categories = categories,
            mostViewedPost = mostViewedPost?.toApiPostResponse(),
            createDate = countResult[4]
        )
    }

}