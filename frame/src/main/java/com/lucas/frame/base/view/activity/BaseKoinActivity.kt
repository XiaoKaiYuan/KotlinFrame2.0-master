package com.lucas.frame.base.view.activity

import android.os.Bundle
import com.lucas.frame.base.presenter.BasePresenter

/**
 * @package    BaseKoinActivity.kt
 * @author     luan
 * @date       2019-12-30
 * @des        提供presenter注入的界面
 */
abstract class BaseKoinActivity< P: BasePresenter<*>> : BaseActivity() {

     abstract val mPresenter:P

     override fun baseInit(savedInstanceState: Bundle?) {
          lifecycle.addObserver(mPresenter)
          super.baseInit(savedInstanceState)
     }
}