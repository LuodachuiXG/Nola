package cc.loac.routes

import cc.loac.data.exceptions.AddFailedException
import cc.loac.data.models.Link
import cc.loac.data.models.enums.LinkSort
import cc.loac.data.requests.LinkRequest
import cc.loac.data.responses.ApiLinkResponse
import cc.loac.data.responses.Pager
import cc.loac.extensions.isEnum
import cc.loac.services.LinkService
import cc.loac.utils.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val linkService: LinkService by inject(LinkService::class.java)

/**
 * 友情链接，管理员路由
 */
fun Route.linkAdminRouting() {
    route("link") {
        authenticate {
            /** 添加友情链接 **/
            post {
                val link = call.receiveByDataClass<LinkRequest> {
                    // 优先级范围 0 - 100
                    it.priority in (0..100)
                }
                call.respondSuccess(linkService.addLink(link)?.also {
                    operate(
                        desc = "添加友情链接 [${link.displayName}]",
                        call = call
                    )
                } ?: throw AddFailedException())
            }

            /** 删除友情链接 **/
            delete {
                val ids = call.receiveByDataClass<List<Long>>()
                if (ids.isEmpty()) call.respondSuccess(false)
                call.respondSuccess(linkService.deleteLinks(ids).also {
                    if (it) {
                        operate(
                            desc = "删除友情链接 [${ids.joinToString(", ")}}",
                            call = call
                        )
                    }
                })
            }

            /** 修改友情链接 **/
            put {
                val link = call.receiveByDataClass<LinkRequest> {
                    // 优先级范围 0 - 100
                    it.priority in (0..100) && it.linkId != null
                }
                call.respondSuccess(linkService.updateLink(link).also {
                    if (it) {
                        operate(
                            desc = "修改友情链接 [${link.displayName}]",
                            call = call
                        )
                    }
                })
            }

            /** 获取友情链接 **/
            get {
                call.receivePageAndSize { page, size ->
                    // 可空的链接排序
                    val sort = call.receiveNullablePathParam("sort") {
                        it?.isEnum<LinkSort>()
                    }?.let { LinkSort.valueOf(it) }
                    call.respondSuccess(linkService.links(page, size, sort))
                }
            }
        }
    }
}

/**
 * 友情链接，API 路由
 */
fun Route.linkApiRouting() {
    route("link") {
        /** 获取友情链接 **/
        get {
            call.receivePageAndSize { page, size ->
                // 将 Link 转为 ApiLinkResponse 数据类，抹去敏感数据
                val linksPager = linkService.links(page, size)
                val resultPager = Pager(
                    page = linksPager.page,
                    size = linksPager.size,
                    data = transformLinkToApiForm(linksPager.data),
                    totalData = linksPager.totalData,
                    totalPages = linksPager.totalPages
                )
                call.respondSuccess(resultPager)
            }
        }
    }
}

/**
 * 将友情链接数据类转换为博客 API 友情链接响应类
 * 此操作可以抹除友情链接数据类中敏感数据
 * @param links 友情链接数据类列表
 */
private fun transformLinkToApiForm(links: List<Link>): List<ApiLinkResponse> {
    return links.map { link ->
        ApiLinkResponse(
            displayName = link.displayName,
            url = link.url,
            logo = link.logo,
            description = link.description,
            isLost = link.isLost
        )
    }
}