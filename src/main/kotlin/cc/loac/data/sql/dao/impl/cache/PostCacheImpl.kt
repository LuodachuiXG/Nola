package cc.loac.data.sql.dao.impl.cache

import cc.loac.data.models.Post
import cc.loac.data.models.PostContent
import cc.loac.data.models.enums.PostContentStatus
import cc.loac.data.models.enums.PostSort
import cc.loac.data.models.enums.PostStatus
import cc.loac.data.models.enums.PostVisible
import cc.loac.data.requests.PostContentRequest
import cc.loac.data.requests.PostRequest
import cc.loac.data.requests.PostStatusRequest
import cc.loac.data.responses.ApiPostResponse
import cc.loac.data.responses.Pager
import cc.loac.data.responses.PostContentResponse
import cc.loac.data.sql.dao.PostDao
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.CacheManagerBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import org.ehcache.impl.config.persistence.CacheManagerPersistenceConfiguration
import java.io.File

interface CacheDao {}

/**
 * 缓存实现
 */
class CacheImpl(
    private val delegate: CacheDao,
    storagePath: File
) : CacheDao {
    // 缓存管理器
    private val cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
        .with(CacheManagerPersistenceConfiguration(storagePath))
        .withCache(
            "postsCache",
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                // 通过文章 ID 作为键，文章对象作为值
                Int::class.javaObjectType,
                Post::class.java,
                ResourcePoolsBuilder.newResourcePoolsBuilder()
                    .heap(1000, EntryUnit.ENTRIES)
                    .offheap(10, MemoryUnit.MB)
                    .disk(100, MemoryUnit.MB, true)
            )
        )
        .build(true)

    private val postsCache = cacheManager.getCache(
        "postsCache", Int::class.javaObjectType, Post::class.java
    )
}