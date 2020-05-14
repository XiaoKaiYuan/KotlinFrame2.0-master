package com.lucas.frame.exception

import java.lang.RuntimeException

/**
 * @package    ARouterException.kt
 * @author     luan
 * @date       2020/3/30
 * @des        阿里路由异常
 */
class ARouterException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

}