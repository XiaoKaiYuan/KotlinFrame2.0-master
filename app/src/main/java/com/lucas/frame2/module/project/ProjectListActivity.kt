package com.lucas.frame2.module.project

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lucas.frame.base.view.activity.BaseRefreshListActivity
import com.lucas.frame.data.net.IBean
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame2.R
import com.lucas.frame2.data.bean.ProjectListBean
import com.lucas.frame2.module.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_project_list.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * @package    ProjectListActivity.kt
 * @author     luan
 * @date       2020-01-07
 * @des        列表
 */
class ProjectListActivity : BaseRefreshListActivity<ProjectListPresenter>() {

    companion object {
        val request_code = 101
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProjectListActivity::class.java)
            activity.startActivityForResult(intent, request_code)
        }
    }

    override val mPresenter: ProjectListPresenter by inject { parametersOf(this) }
    override val layoutId: Int = R.layout.activity_project_list

    override fun initView(savedInstanceState: Bundle?) {
        val adapter = get<ProjectAdapter>()
        v_list.bind(LinearLayoutManager(this), adapter, this, refreshLayout)
    }

    override fun initData(savedInstanceState: Bundle?) {
        mPresenter.loadData()
    }

    override fun onRequestSuccess(
        bean: IBean,
        requestTag: String,
        requestListModel: RequestCall.RequestListModel
    ) {
        if (bean is ProjectListBean) {
            refreshLayout?.isRefreshing = false
            v_list.notifyAdapter(bean.data.datas, requestListModel)
        }
    }

    override fun onRefresh() {
        mPresenter.loadData()
    }

    override fun onLoadMore(recyclerView: RecyclerView, page: Int) {
        mPresenter.loadData(page)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }


}
