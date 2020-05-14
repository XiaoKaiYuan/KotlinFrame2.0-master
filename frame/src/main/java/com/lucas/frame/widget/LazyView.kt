package com.lucas.frame.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.lucas.frame.BaseApp

/**
 * @package    LazyView.kt
 * @author     luan
 * @date       2019-12-26
 * @des        用于懒加载监听跟布局是否可见
 */
class LazyView:FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    //是否是首次可见
    var isFirstVisib = false
    var onFirstVisibility:()->Unit={}

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (!isFirstVisib){
            isFirstVisib = true
            onFirstVisibility()
        }
    }
}