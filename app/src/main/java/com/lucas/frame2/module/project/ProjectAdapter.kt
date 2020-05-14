package com.lucas.frame2.module.project

import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lucas.frame.base.view.adapter.BaseAdapter
import com.lucas.frame2.R
import com.lucas.frame2.data.bean.ProjectListBean

/**
 * @package    ProjectAdapter.kt
 * @author     luan
 * @date       2020-01-07
 * @des
 */
class ProjectAdapter :
    BaseAdapter<ProjectListBean.DataBean.DatasBean, BaseViewHolder>(R.layout.item_project),
    LoadMoreModule {
    override fun convertView(helper: BaseViewHolder, item: ProjectListBean.DataBean.DatasBean) {
        helper.setText(R.id.v_text, item.title)
    }
}