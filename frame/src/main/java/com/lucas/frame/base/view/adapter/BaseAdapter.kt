package com.lucas.frame.base.view.adapter

import androidx.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @package    BaseAdapter.kt
 * @author     luan
 * @date       2020-01-06
 * @des        提供一层adapter基类，供BaseRefreshListActivity引用泛型，并做些简单处理
 *             实现LoadMoreModule接口可实现加载更多
 */
abstract class BaseAdapter<T, VH : BaseViewHolder>(@LayoutRes private val layoutResId: Int) :
    BaseQuickAdapter<T, VH>(layoutResId, null) {
    override fun convert(helper: VH, item: T?) {
        if (item != null)
            convertView(helper, item)
    }

    abstract fun convertView(helper: VH, item: T)


}