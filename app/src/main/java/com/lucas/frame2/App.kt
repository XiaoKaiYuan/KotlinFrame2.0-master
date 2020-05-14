package com.lucas.frame2

import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.lucas.frame.BaseApp
import com.lucas.frame.config.FrameConstants
import com.lucas.frame.config.FrameInitConfig
import com.lucas.frame.data.net.interceptor.CacheCookieInterceptor
import com.lucas.frame.data.net.interceptor.PutCookieInterceptor
import com.lucas.frame2.aop.AuthorImp
import com.lucas.frame2.data.appModules
import com.lucas.frame2.koin.adapterModules
import com.lucas.frame2.koin.presenterModule

/**
 * @package    App.kt
 * @author     luan
 * @date       2019-12-23
 * @des        程序入口
 */
class App: BaseApp() {

    override fun appInit() {
        //初始化参数
        FrameInitConfig.apply {
            setAuthorImp(AuthorImp())
            models.addAll(appModules)
            models.add(adapterModules)
            models.add(presenterModule)
            //同步服务器cookie
            interceptors.add(CacheCookieInterceptor())
            interceptors.add(PutCookieInterceptor())
        }
        //初始化域名
        FrameConstants.HOST = BuildConfig.HOST
        initRefreshView()
    }

    //设置全局load more样式
    private fun initRefreshView() {
        LoadMoreModuleConfig.defLoadMoreView = SimpleLoadMoreView()
    }

}