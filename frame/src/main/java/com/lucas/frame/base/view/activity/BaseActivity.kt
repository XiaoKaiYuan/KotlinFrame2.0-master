package com.lucas.frame.base.view.activity

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.lucas.frame.BaseApp
import com.lucas.frame.R
import com.lucas.frame.base.imp.IView
import com.lucas.frame.exception.FrameException
import com.lucas.frame.helper.ViewHelper
import com.lucas.frame.window.DefaultLoadingDialog
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * @package    BaseActivity.kt
 * @author     luan
 * @date       2019-09-19
 * @des        activity底层基类，提供一些公共的方法，完成部分初始化工作
 */
abstract class BaseActivity : AppCompatActivity(), IView, ViewHelper {

    //布局ID
    abstract val layoutId: Int
    val loadingDialog by inject<DefaultLoadingDialog> { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //arouter注入
        ARouter.getInstance().inject(this)
        //检查application类型
        if (application !is BaseApp) {
            throw FrameException("App model未继承BaseApp或者manifest中未添加自定义的Application")
        }
        baseInit(savedInstanceState)
        initView(savedInstanceState)
        initData(savedInstanceState)
        initEvent()
    }

    //基类初始化
    open fun baseInit(savedInstanceState: Bundle?) {
        setContentView(layoutId)
        findViewById<View>(R.id.frame_back)?.setOnClickListener {
            onBackPressed()
        }
    }

    //布局初始化
    abstract fun initView(savedInstanceState: Bundle?)

    //数据初始化
    abstract fun initData(savedInstanceState: Bundle?)

    //事件初始化
    fun initEvent() {}

    //显示加载弹窗
    override fun showLoadingDialog(msg: String?) {
        if (msg != null)
            loadingDialog.changeText(msg)
        if (!loadingDialog.isShowing)
            loadingDialog.show()
    }

    //隐藏加载弹窗
    override fun hideLoadingDialog() {
        if (loadingDialog.isShowing)
            loadingDialog.dismiss()
    }

    //统一处理返回事件，外部重写该方法。
    override fun onBackPressed() {
        super.onBackPressed()
    }

    //禁止APP字体跟随系统字体大小改变
    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = Configuration()
        configuration.setToDefaults()
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return resources
    }
}