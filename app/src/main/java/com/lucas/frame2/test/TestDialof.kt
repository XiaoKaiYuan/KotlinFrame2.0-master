package com.lucas.frame2.test

import android.app.Activity
import com.lucas.frame.window.DefaultLoadingDialog

class TestDialof(activity: Activity, loadingText: String?) :
    DefaultLoadingDialog(activity, loadingText) {
    override val layoutID: Int =0
}