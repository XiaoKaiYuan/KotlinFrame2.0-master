package com.lucas.frame.arouter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @package    SchemeFilterActivity.kt
 * @author     luan
 * @date       2020/3/30
 * @des        通过URL跳转中转路由   跳转方式：arouter://m.aliyun.com/test/main
 */
class SchemeFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().build(intent.data).navigation()
        finish()
    }
}