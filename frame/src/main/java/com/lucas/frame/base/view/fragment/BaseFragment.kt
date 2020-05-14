package com.lucas.frame.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lucas.frame.R
import com.lucas.frame.base.imp.IView
import com.lucas.frame.helper.ViewHelper
import com.lucas.frame.widget.LazyView
import com.lucas.frame.window.DefaultLoadingDialog
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * @package    BaseFragment.kt
 * @author     luan
 * @date       2019-12-26
 * @des        fragment 基本封装+懒加载(视图可见时才加载数据)
 */
abstract class BaseFragment : Fragment() , IView, ViewHelper {
    //布局ID
    abstract val layoutId: Int

    val loadingDialog by inject<DefaultLoadingDialog>{ parametersOf(this.activity)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            val rootView =
                LayoutInflater.from(container.context).inflate(layoutId, null, false)
            val baseInitView = baseInit(rootView)
            val lazyView = LazyView(container.context)
            lazyView.addView(baseInitView)
            lazyView.onFirstVisibility = {
                initData()
            }
            return lazyView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    //基类初始化
    open fun baseInit(rootView: View):View {
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
        initEvent()
    }

    //初始化布局
    abstract fun initView(rootView: View, savedInstanceState: Bundle?)

    //初始化数据
    abstract fun initData()

    //初始化事件
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
}