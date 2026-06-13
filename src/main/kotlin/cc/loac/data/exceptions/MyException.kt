package cc.loac.data.exceptions

/**
 * 自定义运行时异常类
 */
open class MyException(errMsg: String) : RuntimeException(errMsg) {
    constructor(errMsg: String, cause: Throwable) : this(errMsg) {
        initCause(cause)
    }
}