package com.lucas.frame.helper

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lucas.frame.BaseApp
import com.lucas.frame.R
import com.lucas.frame.base.imp.IRefresh
import com.lucas.frame.base.view.activity.BaseRefreshListActivity
import com.lucas.frame.base.view.adapter.BaseAdapter
import com.lucas.frame.config.FrameConstants

/**
 * @package    ViewHelper.kt
 * @author     luan
 * @date       2020-01-06
 * @des        提供一些view相关的便捷方法
 */
interface ViewHelper {

    /**
     * 处理swiperefresh与recycler滑动冲突
     * @param swipeRefreshLayout SwipeRefreshLayout
     * @param recyclerView RecyclerView
     */
    fun fixRefreshAndRecyclerViewScroll(
        swipeRefreshLayout: SwipeRefreshLayout,
        recyclerView: RecyclerView
    ) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager
                if (manager is LinearLayoutManager) {
                    val position = manager.findFirstCompletelyVisibleItemPosition()
                    swipeRefreshLayout.isEnabled = position == 0
                }
            }
        })
    }


    /**
     * 批量设置点击事件
     * @receiver Array<T>
     * @param onClickListener OnClickListener
     */
    fun <T : View> Array<T>.addClickListener(onClickListener: View.OnClickListener) {
        forEach {
            it.setOnClickListener(onClickListener)
        }
    }

    /**
     * 初始化list view，并且绑定adapter
     * @receiver V RecyclerView
     * @param manager LayoutManager
     * @param adapter BaseAdapter<*, *>
     * @param itemClickListener OnItemClickListener
     * @param iRefresh IRefresh  被绑定的刷新控件
     * @param pageParam PageParam? 分页参数，填null使用默认参数
     */
    fun <V : RecyclerView> V.bind(
        manager: RecyclerView.LayoutManager,
        adapter: BaseAdapter<*, *>,
        itemClickListener: OnItemClickListener,
        iRefresh: IRefresh? = null,
        pageParam: BaseRefreshListActivity.PageParam? = null
    ) {
        layoutManager = manager
        this.adapter = adapter
        //关联加载更多功能
//        adapter.onAttachedToRecyclerView(this)
        adapter.setOnItemClickListener(itemClickListener)
        //绑定分页参数
        val param = BaseRefreshListActivity.PageParam().apply {
            currentPage = FrameConstants.Page.START_INDEX
            pageCount = FrameConstants.Page.PAGE_COUNT
        }
        this.setTag(R.id.frame_tag_page, param)
        //绑定刷新控件
        if (iRefresh != null) {
            this.setTag(R.id.frame_tag_refresh_view, iRefresh)
        }
        //处理冲突
        if (iRefresh is SwipeRefreshLayout){
            fixRefreshAndRecyclerViewScroll(iRefresh,this)
        }
    }

    /**
     * 快捷输出日志 debug
     * @receiver Any
     * @param tag String 日志标记
     */
    fun Any.ld(tag: String = "ViewHelper") {
        Log.d(tag, toStr())
    }

    /**
     * 快捷输出日志 error
     * @receiver Any
     * @param tag String 日志标记
     */
    fun Any.le(tag: String = "ViewHelper") {
        Log.e(tag, toStr())
    }

    /**
     * 快捷显示toast
     * @receiver Any 任意对象，自动调用toString
     * @param duration Int 显示时长
     * @param gravity Int 对齐方式
     */
    @SuppressLint("ShowToast")
    fun Any.toast(duration: Int = 0, gravity: Int = -1) {
        val toast: Toast
        if (duration == 0) {
            toast = Toast.makeText(BaseApp.application, toStr(), Toast.LENGTH_SHORT)
        } else {
            toast = Toast.makeText(BaseApp.application, toStr(), duration)
        }
        if (gravity != -1)
            toast.setGravity(gravity, 0, 0)
        toast.show()
    }

    /**
     * 重写toString防止空指针
     * @receiver Any?
     */
    fun Any?.toStr(): String {
        return this?.toString() ?: "null"
    }
}