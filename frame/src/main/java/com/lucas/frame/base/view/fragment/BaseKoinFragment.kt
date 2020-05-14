package com.lucas.frame.base.view.fragment

import android.view.View
import com.lucas.frame.base.presenter.BasePresenter

/**
 * @package    BaseKoinFragment.kt
 * @author     luan
 * @date       2020-01-07
 * @des         提供presenter注入的界面
 */
abstract class BaseKoinFragment< P: BasePresenter<*>> : BaseFragment() {
    abstract val mPresenter:P

    override fun baseInit(rootView: View): View {
        lifecycle.addObserver(mPresenter)
        return super.baseInit(rootView)
    }
}