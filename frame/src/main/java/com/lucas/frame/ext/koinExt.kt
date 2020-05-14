package com.lucas.frame.ext

import org.koin.core.context.GlobalContext

/**
 * @package    koinExt.kt
 * @author     luan
 * @date       2019-12-26
 * @des        koin 扩展函数
 */

//扩展koin inject注入方法，原方法只能针对ComponentCallbacks 的组建注入，现方法可以在任意对象总注入
inline fun <reified T:Any> Any.injectExt():Lazy<T> = GlobalContext.get().koin.inject()