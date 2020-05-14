package com.lucas.frame.data.net.interceptor

import com.blankj.utilcode.util.SPUtils
import com.lucas.frame.config.FrameConstants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @package    CacheCookieInterceptor.kt
 * @author     luan
 * @date       2020-01-07
 * @des        缓存服务器cookie
 */
class CacheCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.headers("Set-Cookie").isNotEmpty()) {
            SPUtils.getInstance()
                .put(FrameConstants.Sp.SP_COOKIE, response.headers("Set-Cookie").toHashSet())
        }
        return response
    }
}