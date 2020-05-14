package com.lucas.frame.base.imp
/**
 * @package    IRefresh.kt
 * @author     luan
 * @date       2020-01-06
 * @des        所有的刷新控件应该实现该接口，以便于BaseRefreshListActivity联动处理
 */
interface IRefresh {

    //结束刷新
    fun finishRefresh()

    //是否正在刷新
    fun isRefresh():Boolean
}