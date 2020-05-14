package com.lucas.frame.data.net
/**
 * @package    ICustomDialog.kt
 * @author     luan
 * @date       2020-01-02
 * @des        配合{@link com.lucas.frame.data.net.RequestCall}使用，
 *              用于实现自定义弹窗.
 */
interface ICustomDialog {
    fun showLoading(msg:String?)
    fun hideLoading()
}