package com.lucas.frame.data.net.interceptor

import com.blankj.utilcode.util.SPUtils
import com.lucas.frame.config.FrameConstants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @package    PutCookieInterceptor.kt
 * @author     luan
 * @date       2020-01-07
 * @des        将缓存的cookie添加到请求头
 */
class PutCookieInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookies = SPUtils.getInstance().getStringSet(FrameConstants.Sp.SP_COOKIE)
        if (cookies.isNotEmpty()){
           cookies.forEach {
               builder.addHeader("Set-Cookie",it)
           }
        }
        return chain.proceed(builder.build())
    }
}