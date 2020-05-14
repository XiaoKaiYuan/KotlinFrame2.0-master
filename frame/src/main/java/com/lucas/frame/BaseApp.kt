package com.lucas.frame

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.just.agentweb.LogUtils.isDebug
import com.lucas.frame.config.FrameConstants
import com.lucas.frame.config.FrameInitConfig
import com.lucas.frame.data.net.model.frameModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * @package    BaseApp.kt
 * @author     luan
 * @date       2019-09-19
 * @des        应用入口
 */
abstract class BaseApp :Application(){

    companion object{
        val TAG = "frame_lucas"
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appInit()
        checkParam()
        baseInit()
    }

    //应用入口初始化
    abstract fun appInit()

    //检查必填参数
    private fun checkParam(){
        if (FrameConstants.HOST.isNullOrEmpty()){
            Log.e(TAG,"域名为空，FrameConfig->HOST")
        }
    }

    private fun baseInit() {
        FrameInitConfig.OKHTTP_CACHE_PATH = "${cacheDir.absolutePath}/net"

        //init koin
        val moduleList = frameModels.toMutableList()
        moduleList.addAll(FrameInitConfig.models)
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@BaseApp)
            androidFileProperties()
            modules(moduleList)
        }
        //init qmui

        //init arouter
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}