package com.lucas.frame.base.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lucas.frame.base.imp.IRequestView
import com.lucas.frame.base.presenter.BasePresenter
import com.lucas.frame.data.net.IBean
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame.exception.FrameException
import com.lucas.frame.exception.FrameHttpException
import com.lucas.frame.widget.loadingview.SwitchLayout
import java.lang.Exception

/**
 * @package    BaseNetActivity.kt
 * @author     luan
 * @date       2020-01-03
 * @des        提供网络访问基本封装
 */
abstract class BaseNetActivity<P : BasePresenter<*>> : BaseKoinActivity<P>(), IRequestView {

    //默认添加一个switch布局到跟布局上
    var isAddSwitchViewToRoot = true

    var rootSwitchView: SwitchLayout? = null

    override fun setContentView(layoutResID: Int) {
        if (isAddSwitchViewToRoot) {
            //在布局的外层添加一层网络状态切换布局作为父容器
            rootSwitchView = SwitchLayout(this)
            val inflate = LayoutInflater.from(this).inflate(layoutResID, null, false)
            if (inflate == null) {
                throw FrameException("layoutResID 资源错误")
            }
            rootSwitchView?.addView(inflate)
            super.setContentView(rootSwitchView)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        if (isAddSwitchViewToRoot) {
            //在布局的外层添加一层网络状态切换布局作为父容器
            rootSwitchView = SwitchLayout(this)
            rootSwitchView?.addView(view)
            super.setContentView(rootSwitchView)
        } else
            super.setContentView(view)
    }

    override fun baseInit(savedInstanceState: Bundle?) {
        super.baseInit(savedInstanceState)

    }

    override fun onRequestFail(
        bean: IBean,
        requestTag: String,
        requestListModel: RequestCall.RequestListModel
    ) {
        if (bean.errorMsg.isEmpty())
            "请求失败".toast()
        else
            bean.errorMsg.toast()
    }

    override fun onRequestError(exception: Exception) {
        if (exception is FrameHttpException) {
            exception.msg.toast()
        }
    }

    override fun tokenOverdue() {
    }

    override fun notLogin() {
    }

    override fun restore() {
    }
}