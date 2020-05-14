package com.lucas.frame.base.view.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lucas.frame.R
import com.lucas.frame.base.imp.IRefresh
import com.lucas.frame.base.presenter.BasePresenter
import com.lucas.frame.base.view.activity.BaseRefreshListActivity
import com.lucas.frame.base.view.adapter.BaseAdapter
import com.lucas.frame.data.net.RequestCall

/**
 * @package    BaseRefreshListFragment.kt
 * @author     luan
 * @date       2020-01-07
 * @des        提供刷新控件和列表控件的关联，以及处理列表分页
 *              该基类分页使用步骤：布局中添加recyclerView->调用ViewHelper.bind()方法进行绑定->获取到数据后调用notifyAdapter()
 */
abstract class BaseRefreshListFragment<P : BasePresenter<*>> : BaseRefreshFragment<P>() {

    //list view的空布局
    var listEmptyView: View? = null

    override fun baseInit(rootView: View): View {
        if (getListEmptyLayoutID() != 0) {
            listEmptyView =
                LayoutInflater.from(activity).inflate(getListEmptyLayoutID(), null, false)
        }
        return super.baseInit(rootView)
    }

    //设置list view的空布局
    fun getListEmptyLayoutID(): Int = R.layout.frame_view_pager_no_data

    /**
     * 更新list view数据，并且完成分页操作
     * @param recyclerView RecyclerView 被操作的列表
     * @param data List<*>  数据源
     * @param requestListModel RequestListModel 数据更新模式，刷新/加载更多
     */
    fun <B> RecyclerView.notifyAdapter(
        data: List<B>,
        requestListModel: RequestCall.RequestListModel
    ) {
        finishRefresh(this)
        //获取分页参数
        val pageParam = getTag(R.id.frame_tag_page) as? BaseRefreshListActivity.PageParam
        if (pageParam != null) {
            val adapter = adapter as? BaseAdapter<B, *>
            //处理分页
            if (requestListModel == RequestCall.RequestListModel.REFRESH) {
                pageParam.currentPage = 1
                //更新缓存
                setTag(R.id.frame_tag_page, pageParam)
                if (data.isEmpty()) {//如果没有数据
                    adapter?.data?.clear()
                    adapter?.notifyDataSetChanged()
                    adapter?.loadMoreModule?.isEnableLoadMore = false
                    if (listEmptyView != null)
                        adapter?.setEmptyView(listEmptyView!!)
                } else {
                    adapter?.loadMoreModule?.loadMoreComplete()
                    adapter?.setNewData(data.toMutableList())
                    //添加加载更多监听
                    adapter?.loadMoreModule?.setOnLoadMoreListener {
                        pageParam.currentPage += 1
                        //更新缓存
                        setTag(R.id.frame_tag_page, pageParam)
                        onLoadMore(this, pageParam.currentPage)
                    }
                }

            } else if (requestListModel == RequestCall.RequestListModel.LOAD_MODE) {
                adapter?.loadMoreModule?.isEnableLoadMore = true
                if (data.isEmpty()) {//没有更多数据
                    adapter?.loadMoreModule?.loadMoreEnd()
                } else {
                    adapter?.loadMoreModule?.loadMoreComplete()
                    adapter?.addData(data)
                }
            }

        } else {
            le("未获取到分页参数，请查看RecyclerView是否已调用ViewHelper.bind()方法")
        }
    }

    //结束刷新
    private fun finishRefresh(recyclerView: RecyclerView) {
        //获取刷新控件
        val iRefresh = recyclerView.getTag(R.id.frame_tag_refresh_view) as? IRefresh
        if (iRefresh != null && iRefresh.isRefresh())
            iRefresh.finishRefresh()
    }

    /**
     * 如果list view刷新或者加载更多失败，需要调用此方法触发交互效果
     * @param recyclerView RecyclerView
     * @param requestListModel RequestListModel
     */
    fun RecyclerView.notifyFail(requestListModel: RequestCall.RequestListModel) {
        val adapter = adapter as? BaseAdapter<*, *>
        //获取分页参数
        val pageParam = getTag(R.id.frame_tag_page) as? BaseRefreshListActivity.PageParam
        if (requestListModel == RequestCall.RequestListModel.REFRESH) {
            finishRefresh(this)
            if (adapter != null) {
                if (adapter.data.isEmpty()) {
                    adapter.loadMoreModule?.isEnableLoadMore = false
                } else {
                    //添加加载更多监听
//                    adapter.loadMoreModule?.setOnLoadMoreListener {
//                        onLoadMore(this, pageParam?.currentPage ?: FrameConfig.Page.START_INDEX)
//                    }
                }

            }

        } else if (requestListModel == RequestCall.RequestListModel.LOAD_MODE) {
            adapter?.loadMoreModule?.loadMoreFail()
        }
    }

    /**
     * 触发加载更多回调
     * @param recyclerView RecyclerView 用于区分哪个列表触发的
     */
    abstract fun onLoadMore(recyclerView: RecyclerView, page: Int)
}