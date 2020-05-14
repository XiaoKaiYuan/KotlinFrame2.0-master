package com.lucas.frame.arouter.interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.lucas.frame.arouter.ARouterExts
import com.lucas.frame.exception.ARouterException

/**
 * @package    LoginInterceptor.kt
 * @author     luan
 * @date       2020/3/30
 * @des        登陆拦截器
 */
// 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
@Interceptor(priority = 1, name = "登陆拦截")
class LoginInterceptor : IInterceptor {
    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        when (postcard.extra) {
            ARouterExts.MUST_LOGIN -> {//需要登陆
            }
            else -> {
            }
        }
        callback.onContinue(postcard)//交换控制权
//        callback.onInterrupt(ARouterException("请先登陆！"))//拦截路由
    }

    // 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    override fun init(context: Context?) {
    }
}