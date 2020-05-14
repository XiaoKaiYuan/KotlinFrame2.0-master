package com.lucas.frame.widget.loadingview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.lucas.frame.R
import java.lang.RuntimeException

/**
 * @package    SwitchLayout.kt
 * @author     luan
 * @date       2019-12-31
 * @des        最多只能有一个孩子,负责切换布局
 */
class SwitchLayout : FrameLayout, SwitchViewController {
    //原始布局
    private var originView: View? = null

    //状态布局容器
    private val statusViews = HashMap<String, View>()

    companion object {
        //默认状态
        val STATUS_ORIGIN = "origin"//原布局
        val STATUS_EMPTY = "empty"//空布局
        val STATUS_SERVER_ERROR = "server_error"//服务器错误
        val STATUS_NET_ERROR = "net_error"//网络错误
        val STATUS_LOADING = "loading"//加载中
        val STATUS_CONN_TIMEOUT = "conn_timeout"//链接超时
        val STATUS_NOT_NETWORK = "not_network"//无网络
    }

    //当前状态
    private var currentStatus = STATUS_ORIGIN

    //默认布局
    private val view_empty = R.layout.frame_view_switch_empty
    private val view_server_error = R.layout.frame_view_switch_server_error
    private val view_net_error = R.layout.frame_view_switch_net_error
    private val view_loading = R.layout.frame_view_switch_loading
    private val view_conn_timeout = R.layout.frame_view_switch_timeout
    private val view_not_network = R.layout.frame_view_switch_not_network

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    //初始化
    fun initView(context: Context) {
        //检查是否只有一个孩子
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                if (childCount > 1) {
                    throw RuntimeException("SwitchLayout布局最多只能有一个孩子.")
                } else {
                    if (childCount > 0) {
                        originView = getChildAt(0)
                    }
                }
            }
        })
        //添加默认状态布局
        statusViews.put(STATUS_EMPTY, LayoutInflater.from(context).inflate(view_empty, null, false))
        statusViews.put(
            STATUS_SERVER_ERROR,
            LayoutInflater.from(context).inflate(view_server_error, null, false)
        )
        statusViews.put(
            STATUS_NET_ERROR,
            LayoutInflater.from(context).inflate(view_net_error, null, false)
        )
        statusViews.put(
            STATUS_LOADING,
            LayoutInflater.from(context).inflate(view_loading, null, false)
        )
        statusViews.put(
            STATUS_CONN_TIMEOUT,
            LayoutInflater.from(context).inflate(view_conn_timeout, null, false)
        )
        statusViews.put(
            STATUS_NOT_NETWORK,
            LayoutInflater.from(context).inflate(view_not_network, null, false)
        )
    }

    override fun switch(status: String) {
        if (currentStatus == status)
            return
        currentStatus = status
        removeAllViews()
        visibility = View.VISIBLE
        if (currentStatus == STATUS_ORIGIN) {
            if (originView == null) {
                visibility = View.GONE
            } else {
                addView(originView)
            }
        } else {
            addView(statusViews[currentStatus])
        }
    }

    override fun restore() {
        currentStatus = STATUS_ORIGIN
        switch(currentStatus)
    }

    override fun currentStatus(): String {
        return currentStatus
    }
}