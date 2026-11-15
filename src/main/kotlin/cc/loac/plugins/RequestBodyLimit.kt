package cc.loac.plugins

import cc.loac.utils.RequestLimits
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*

/**
 * 根据 Content-Length 拒绝超过限制的请求体
 * 对于没有 Content-Length 的流式请求，由具体上传/导入逻辑做读取上限校验
 */
val RequestBodyLimit = createApplicationPlugin("RequestBodyLimit") {
    onCall { call ->
        val contentLength = call.request.header(HttpHeaders.ContentLength)?.toLongOrNull()
            ?: return@onCall
        val maxBytes = if (call.request.contentType().contentType.equals("multipart", ignoreCase = true)) {
            RequestLimits.MAX_MULTIPART_BODY_BYTES
        } else {
            RequestLimits.MAX_JSON_BODY_BYTES
        }
        if (contentLength > maxBytes) throw PayloadTooLargeException(maxBytes)
    }
}

/**
 * 配置请求体大小限制插件
 */
fun Application.configureRequestBodyLimit() {
    install(RequestBodyLimit)
}
