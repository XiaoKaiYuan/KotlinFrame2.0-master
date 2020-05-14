package com.lucas.frame.data.net

import android.app.Application
import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonParseException
import com.lucas.frame.R
import com.lucas.frame.base.imp.IRequestView
import com.lucas.frame.config.FrameConstants
import com.lucas.frame.exception.FrameHttpException
import com.lucas.frame.ext.injectExt
import com.lucas.frame.widget.loadingview.SwitchLayout
import com.lucas.frame.widget.loadingview.SwitchViewController
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLHandshakeException

/**
 * @package    RequestCall.kt
 * @author     luan
 * @date       2020-01-02
 * @des        调用请求
 */
class RequestCall(
    val iView: IRequestView? = null,
    val switchViewController: SwitchViewController? = null
) {

    val TAG = "RequestCall"

    //请求
    lateinit var call: Observable<out IBean>

    //请求成功回调
    var onSuccess: (bean: IBean, requestTag: String) -> Unit = { bean, requestTag -> }

    //请求失败回调
    var onFail: (bean: IBean, requestTag: String) -> Unit = { bean, requestTag -> }

    //请求错误回调
    var onError: (e: Exception) -> Unit = {}

    //设置加载样式
    var loadStyle = LoadStyle.NONE

    //设置请求的列表数据模式
    var requestListModel = RequestListModel.REFRESH

    //设置自定义加载弹窗样式
    var customDialog: ICustomDialog? = null

    val app: Application by injectExt()

    //请求标记，用于相同Bean类型的请在回调中区分请求体
    var requestTag = ""

    //加载样式
    enum class LoadStyle {
        NONE,//什么都不显示
        VIEW,//显示布局样式
        DIALOG,//显示popupWindow样式
        VIEW_AND_DIALOG,//显示布局和dialog样式
        CUSTOM_DIALOG//显示自定义弹窗样式
    }

    //请求列表结构数据模式
    enum class RequestListModel {
        REFRESH,//刷新数据
        LOAD_MODE//加载更多
    }

    //发起请求
    fun request(init: RequestCall.() -> Unit): Disposable? {
        init()
        showLoadingStyle()
        //检查网络状态
        if (!checkNetStatus()) return null
        val observable = call.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return observable?.subscribe({
            //如果code==200
            analyzeJsonCode(it)
        }, {
            analyzeErrorCode(it)
        }, {

        })
    }

    //分析错误码
    private fun analyzeErrorCode(throwable: Throwable) {
        iView?.restore()
        if (loadStyle == LoadStyle.DIALOG || loadStyle == LoadStyle.VIEW_AND_DIALOG) {
            iView?.hideLoadingDialog()
        }
        if (loadStyle == LoadStyle.CUSTOM_DIALOG) {
            customDialog?.hideLoading()
        }
        when (throwable) {
            is HttpException -> {//errorCode 错误
                val errorBody = throwable.response().errorBody()
                when (throwable.code()) {
                    FrameConstants.NetCode.NOT_LOGIN -> {//未登录
                        ToastUtils.showShort(app.resources.getString(R.string.frame_user_not_login))
                        iView?.notLogin()
                    }
                    FrameConstants.NetCode.TOKEN_OVERDUE -> {//token过期
                        ToastUtils.showShort(app.resources.getString(R.string.frame_user_token_overdue))
                        iView?.tokenOverdue()
                    }
                    FrameConstants.NetCode.RULE_ERROR -> {//404
                        switchViewController?.switch(SwitchLayout.STATUS_NET_ERROR)
                        iView?.onRequestFail(
                            IBean(throwable.code(), ""),
                            requestTag,
                            requestListModel
                        )
                        onFail(IBean(throwable.code(), ""), requestTag)
                    }
                    else -> {//其他错误码
                        switchViewController?.switch(SwitchLayout.STATUS_SERVER_ERROR)
                        iView?.onRequestFail(
                            IBean(throwable.code(), ""),
                            requestTag,
                            requestListModel
                        )
                        onFail(IBean(throwable.code(), ""), requestTag)
                    }
                }
            }
            is JsonParseException -> {//解析错误
                switchViewController?.switch(SwitchLayout.STATUS_SERVER_ERROR)
                iView?.onRequestError(FrameHttpException("解析错误"))
                onError(throwable)
            }
            is ConnectException -> {//链接错误
                switchViewController?.switch(SwitchLayout.STATUS_SERVER_ERROR)
                iView?.onRequestError(FrameHttpException("链接错误"))
                onError(throwable)
            }
            is SocketTimeoutException -> {//链接超时
                switchViewController?.switch(SwitchLayout.STATUS_CONN_TIMEOUT)
                iView?.onRequestError(FrameHttpException("链接超时"))
                onError(throwable)
            }
            is SSLHandshakeException -> {//证书错误
                switchViewController?.switch(SwitchLayout.STATUS_SERVER_ERROR)
                iView?.onRequestError(FrameHttpException("证书错误"))
                onError(throwable)
            }
            else -> {//其他错误
                switchViewController?.switch(SwitchLayout.STATUS_SERVER_ERROR)
                if (throwable is Exception) {
                    iView?.onRequestError(FrameHttpException("服务器繁忙"))
                    onError(throwable)
                }
            }
        }
    }

    //分析json中的code
    private fun analyzeJsonCode(ibean: IBean) {
        iView?.restore()
        finishLoading()
        when (ibean.errorCode) {
            FrameConstants.NetCode.REQUEST_SUCCESS -> {//请求成功
                onSuccess(ibean, requestTag)
                if (ibean.isEmpty() && (loadStyle == LoadStyle.VIEW || loadStyle == LoadStyle.VIEW_AND_DIALOG))
                    switchViewController?.switch(SwitchLayout.STATUS_EMPTY)
                iView?.onRequestSuccess(ibean, requestTag, requestListModel)
            }
            FrameConstants.NetCode.NOT_LOGIN -> {//未登录
                ToastUtils.showShort(app.resources.getString(R.string.frame_user_not_login))
                iView?.notLogin()
            }
            FrameConstants.NetCode.TOKEN_OVERDUE -> {//token过期
                ToastUtils.showShort(app.resources.getString(R.string.frame_user_token_overdue))
                iView?.tokenOverdue()
            }
            else -> {//其他错误
                iView?.onRequestFail(ibean, requestTag, requestListModel)
            }
        }
    }

    //结束加载
    private fun finishLoading() {
        when (loadStyle) {
            LoadStyle.NONE -> {
            }
            LoadStyle.VIEW -> {
                switchViewController?.switch(SwitchLayout.STATUS_ORIGIN)
            }
            LoadStyle.DIALOG -> {
                iView?.hideLoadingDialog()
            }
            LoadStyle.VIEW_AND_DIALOG -> {
                iView?.hideLoadingDialog()
                switchViewController?.switch(SwitchLayout.STATUS_ORIGIN)
            }
            LoadStyle.CUSTOM_DIALOG -> {
                customDialog?.hideLoading()
            }
        }
    }

    //检查网络状态
    private fun checkNetStatus(): Boolean {
        val status = NetworkUtils.isConnected()
        if (!status) {
            //网络不可用
            "网络不可用".p()
            ToastUtils.showShort(app.resources.getString(R.string.frame_switch_not_network))
            if (loadStyle == LoadStyle.VIEW || loadStyle == LoadStyle.VIEW_AND_DIALOG) {
                switchViewController?.switch(SwitchLayout.STATUS_NOT_NETWORK)
            }

        }
        return status
    }

    //显示加载中样式
    private fun showLoadingStyle() {
        when (loadStyle) {
            LoadStyle.NONE -> {
            }
            LoadStyle.VIEW -> {
                switchViewController?.switch(SwitchLayout.STATUS_LOADING)
            }
            LoadStyle.DIALOG -> {
                iView?.showLoadingDialog(app.resources.getString(R.string.frame_dialog_loading))
            }
            LoadStyle.VIEW_AND_DIALOG -> {
                iView?.showLoadingDialog(app.resources.getString(R.string.frame_dialog_loading))
                switchViewController?.switch(SwitchLayout.STATUS_LOADING)
            }
            LoadStyle.CUSTOM_DIALOG -> {
                customDialog?.showLoading(app.resources.getString(R.string.frame_dialog_loading))
            }
        }
    }

    fun Any.p() {
        Log.d(TAG, this.toString())
    }
}