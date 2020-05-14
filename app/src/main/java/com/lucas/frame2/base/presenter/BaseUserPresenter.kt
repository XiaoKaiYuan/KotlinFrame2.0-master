package com.lucas.frame2.base.presenter

import com.lucas.frame.base.imp.IView
import com.lucas.frame.base.presenter.BasePresenter
import java.lang.ref.Reference
import java.lang.ref.SoftReference

/**
 * @package    BaseUserPresenter.kt
 * @author     luan
 * @date       2020-01-06
 * @des        提供一些用户相关的操作
 */
abstract class BaseUserPresenter<V : IView>(val mView: SoftReference<V>) : BasePresenter<V>(mView) {

    //刷新用户信息
    fun refreshUserInfo(){

    }

    //提交修改的用户信息
    fun updataUserInfo(){

    }
}