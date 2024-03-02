package cc.loac.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 自动协程
 * @param block 协程体
 * @param scope 协程上下文
 */
fun launchCoroutine(
    scope: CoroutineContext = Dispatchers.IO,
    block: suspend () -> Unit
) {
    CoroutineScope(scope).launch { block() }
}