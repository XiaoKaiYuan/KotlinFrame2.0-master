package com.lucas.frame.base.imp

import com.lucas.frame.data.net.IBean
import com.lucas.frame.data.net.RequestCall
import java.lang.Exception

/**
 * @package    IRequestView.kt
 * @author     luan
 * @date       2020-01-02
 * @des        请求c层实现接口
 */
interface IRequestView:IView {
    /**
     * 请求成功
     * @param bean IBean 返回的数据
     * @param requestTag String 请求对应的标记
     * @param requestListModel RequestListModel 加载类型
     */
    fun onRequestSuccess(bean: IBean,requestTag:String,requestListModel: RequestCall.RequestListModel)
    //请求失败
    fun onRequestFail(bean: IBean,requestTag:String,requestListModel: RequestCall.RequestListModel)
    //请求错误
    fun onRequestError(exception: Exception)
    //token 过期
    fun tokenOverdue()
    //未登录
    fun notLogin()
    //重置状态,在请求错误或者结束市调用，用于关闭一些过度控件
    fun restore()
}