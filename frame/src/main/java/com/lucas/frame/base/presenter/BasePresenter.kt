package com.lucas.frame.base.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.lucas.frame.base.imp.ILifecycleObserver
import com.lucas.frame.base.imp.IPresenter
import com.lucas.frame.base.imp.IRequestView
import com.lucas.frame.base.imp.IView
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame.helper.ViewHelper
import com.lucas.frame.widget.loadingview.SwitchViewController
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.SoftReference

/**
 * @package    BasePresenter.kt
 * @author     luan
 * @date       2020-01-06
 * @des        p层基类,捆绑生命周期,提供一些基本功能
 */
abstract class BasePresenter<V : IView>(val mV: SoftReference<V>) : IPresenter, ViewHelper,
    ILifecycleObserver {

    val compositeDisposable = CompositeDisposable()

    //请求数据
    fun request(switchViewController: SwitchViewController? = null, init: RequestCall.() -> Unit) {
        val v = mV.get()
        var disposable: Disposable?
        if (v is IRequestView)
            disposable = RequestCall(v, switchViewController).request(init)
        else
            disposable = RequestCall(null, switchViewController).request(init)
        if (disposable != null)
            compositeDisposable.add(disposable)
    }

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
        mV.clear()
        compositeDisposable.clear()
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
    }
}