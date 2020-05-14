package com.lucas.frame.base.view.fragment

import android.content.Context
import android.view.View
import com.lucas.frame.base.imp.IRequestView
import com.lucas.frame.base.presenter.BasePresenter
import com.lucas.frame.data.net.IBean
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame.exception.FrameHttpException
import com.lucas.frame.widget.loadingview.SwitchLayout
import java.lang.Exception

/**
 * @package    BaseNetFragment.kt
 * @author     luan
 * @date       2020-01-07
 * @des        提供网络访问基本封装
 */
abstract class BaseNetFragment<P : BasePresenter<*>> : BaseKoinFragment<P>() ,IRequestView{
    //默认添加一个switch布局到跟布局上
    var isAddSwitchViewToRoot = true

    var rootSwitchView: SwitchLayout? = null

    override fun baseInit(rootView: View): View {
        if (isAddSwitchViewToRoot) {
            //在布局的外层添加一层网络状态切换布局作为父容器
            rootSwitchView = SwitchLayout(activity as Context)
            rootSwitchView?.addView(rootView)
            return rootSwitchView as View
        } else
            return super.baseInit(rootView)
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