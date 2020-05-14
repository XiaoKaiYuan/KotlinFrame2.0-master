package com.lucas.frame.window

import android.app.Activity
import android.widget.TextView
import com.lucas.frame.R
import com.lucas.frame.base.view.window.BaseDialog
import kotlinx.android.synthetic.main.frame_dialog_default.*
/**
 * @package    DefaultLoadingDialog.kt
 * @author     luan
 * @date       2019-12-30
 * @des        默认加载框
 */
open class DefaultLoadingDialog(activity: Activity, val loadingText: String? = null) :
    BaseDialog(activity) {

    override val layoutID: Int = R.layout.frame_dialog_default

    override fun initView() {
        if (loadingText!=null){
            findViewById<TextView>(R.id.frame_loading_dialog_text)?.text = loadingText
        }
    }

    //修改提示语
    fun changeText(msg:String){
        findViewById<TextView>(R.id.frame_loading_dialog_text)?.text = msg
    }

}