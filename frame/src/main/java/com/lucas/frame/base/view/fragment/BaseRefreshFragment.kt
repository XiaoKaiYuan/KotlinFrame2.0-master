package com.lucas.frame.base.view.fragment

import android.content.Context
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lucas.frame.base.presenter.BasePresenter
import com.lucas.frame.widget.VpSwipeRefreshLayout
/**
 * @package    BaseRefreshFragment.kt
 * @author     luan
 * @date       2020-01-07
 * @des         提供刷新控件，并关联控件联动
 */
abstract class BaseRefreshFragment<P : BasePresenter<*>> : BaseNetFragment<P>(),
    SwipeRefreshLayout.OnRefreshListener {

    //是否默认添加一个SwipeRefreshLayout在根布局
    var isAutoAddSwipeRefreshLayout = true

    var refreshLayout: VpSwipeRefreshLayout? = null

    override fun baseInit(rootView: View): View {
        if (isAutoAddSwipeRefreshLayout) {
            refreshLayout = VpSwipeRefreshLayout(activity as Context)
            refreshLayout?.addView(rootView)
            refreshLayout?.setOnRefreshListener(this)
            return refreshLayout as View
        } else
            return super.baseInit(rootView)
    }

    override fun restore() {
        refreshLayout?.isRefreshing = false
    }
}