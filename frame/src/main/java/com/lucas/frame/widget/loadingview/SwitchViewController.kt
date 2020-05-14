package com.lucas.frame.widget.loadingview

/**
 * @package    SwitchViewController.kt
 * @author     luan
 * @date       2019-12-31
 * @des        切换布局控制接口
 */
interface SwitchViewController {
    //根据状态切换布局
    fun switch(status: String)

    //重置状态
    fun restore()

    //获取当前状态
    fun currentStatus():String
}