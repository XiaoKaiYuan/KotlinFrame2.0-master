package com.lucas.frame.base.view.activity

import android.view.LayoutInflater
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lucas.frame.base.presenter.BasePresenter
import com.lucas.frame.exception.FrameException
import com.lucas.frame.widget.VpSwipeRefreshLayout

/**
 * @package    BaseRefreshActivity.kt
 * @author     luan
 * @date       2020-01-06
 * @des        提供刷新控件，并关联控件联动
 */
abstract class BaseRefreshActivity<P : BasePresenter<*>> : BaseNetActivity<P>(),
    SwipeRefreshLayout.OnRefreshListener {
    //是否默认添加一个SwipeRefreshLayout在根布局
    var isAutoAddSwipeRefreshLayout = true

    var refreshLayout: VpSwipeRefreshLayout? = null

    override fun setContentView(layoutResID: Int) {
        if (isAutoAddSwipeRefreshLayout) {
            refreshLayout = VpSwipeRefreshLayout(this)
            val inflate = LayoutInflater.from(this).inflate(layoutResID, null, false)
            if (inflate == null) {
                throw FrameException("layoutResID 资源错误")
            }
            refreshLayout?.addView(inflate)
            refreshLayout?.setOnRefreshListener(this)
            super.setContentView(refreshLayout)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View?) {
        if (isAutoAddSwipeRefreshLayout && view != null) {
            refreshLayout = VpSwipeRefreshLayout(this)
            refreshLayout?.addView(view)
            refreshLayout?.setOnRefreshListener(this)
            super.setContentView(refreshLayout)
        } else {
            super.setContentView(view)
        }
    }

    override fun restore() {
        refreshLayout?.isRefreshing = false
    }
}