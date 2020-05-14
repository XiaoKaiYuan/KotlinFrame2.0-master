package com.lucas.frame.config

import com.lucas.frame.aop.port.IAuthor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module

/**
 * @package    FrameInitConfig.kt
 * @author     luan
 * @date       2019-09-05
 * @des        框架初始化参数配置
 */
object FrameInitConfig {

    private var iAuthor: IAuthor? = null

    //okhttp缓存位置
    var OKHTTP_CACHE_PATH: String? = null
    //okhttp缓存大小
    var OKHTTP_CACHE_SIZE: Long = 10 * 1024 * 1024

    //拦截器
    val interceptors = ArrayList<Interceptor>()

    //model
    val models = ArrayList<Module>()

    init {
        interceptors.add(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }

    fun setAuthorImp(iAuthor: IAuthor): FrameInitConfig {
        this.iAuthor = iAuthor
        return this
    }

    fun getAuthorImp() = iAuthor
}