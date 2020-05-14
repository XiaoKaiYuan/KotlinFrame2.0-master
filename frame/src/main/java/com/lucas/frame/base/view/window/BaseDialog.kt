package com.lucas.frame.base.view.window

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.lucas.frame.base.imp.ILifecycleObserver

/**
 * @package    BaseDialog.kt
 * @author     luan
 * @date       2019-12-30
 * @des        弹窗基类
 */
abstract class BaseDialog(val mContent: Context) : Dialog(mContent) {
    //布局ID
    abstract val layoutID: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseInitView()
        initView()
    }

    private fun baseInitView() {
        setContentView(layoutID)
        //绑定activity生命周期
        bindLifecycle(mContent)
        //居中
        val attributes = window?.attributes
        attributes?.gravity = resetGravity()
        //宽度包裹
        attributes?.width = resetWidth()
        attributes?.height = resetHeight()
    }

    private fun bindLifecycle(mContent: Context) {
        var lifecycle:Lifecycle? =null
        if (mContent is AppCompatActivity) {
             lifecycle = mContent.lifecycle
        }
        if (mContent is Fragment){
            lifecycle = mContent.lifecycle
        }
        lifecycle?.addObserver(object : ILifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
            }

            override fun onStart(owner: LifecycleOwner) {
            }

            override fun onResume(owner: LifecycleOwner) {
            }

            override fun onPause(owner: LifecycleOwner) {
            }

            override fun onStop(owner: LifecycleOwner) {
            }

            override fun onDestroy(owner: LifecycleOwner) {
                dismiss()
            }

            override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
            }
        })
    }

    abstract fun initView()

    override fun show() {
        //防止窗口泄漏
        if (mContent is Activity && mContent.isDestroyed)
            return
        super.show()
    }

    open fun resetWidth() = WindowManager.LayoutParams.WRAP_CONTENT
    open fun resetHeight() = WindowManager.LayoutParams.WRAP_CONTENT
    open fun resetGravity() = Gravity.CENTER

}