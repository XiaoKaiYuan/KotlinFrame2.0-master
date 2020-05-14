package com.lucas.frame.data.net.model

import android.app.Activity
import com.google.gson.Gson
import com.lucas.frame.config.FrameConstants
import com.lucas.frame.config.FrameInitConfig
import com.lucas.frame.window.DefaultLoadingDialog
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


//网络相关
val netModule = module {
    //app context
//    single { BaseApp.application }

    //gson
    single { Gson() }

    //网络缓存
    single { Cache(File(FrameInitConfig.OKHTTP_CACHE_PATH!!), FrameInitConfig.OKHTTP_CACHE_SIZE) }

    //okhttp配置
    single {
        val builder = OkHttpClient.Builder()
        FrameInitConfig.interceptors.forEach {
            builder.addInterceptor(it)
        }
        builder.connectTimeout(FrameConstants.NET_CONN_TIMEOUT, TimeUnit.SECONDS)
        builder.readTimeout(FrameConstants.NET_READ_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(FrameConstants.NET_WRITE_TIMEOUT, TimeUnit.SECONDS)
        builder.cache(get())
        builder.build()
    }

    //retrofit配置
    single {
        Retrofit
            .Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .baseUrl(FrameConstants.HOST)
            .build()
    }
}

//功能性组建
val functionModule = module {
    factory { (activity: Activity) -> DefaultLoadingDialog(activity) }
}

val frameModels = listOf(netModule, functionModule)