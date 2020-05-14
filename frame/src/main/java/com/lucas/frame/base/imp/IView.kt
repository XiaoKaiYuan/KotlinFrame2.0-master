package com.lucas.frame.base.imp

interface IView {
    //显示loading弹窗
    fun showLoadingDialog(msg:String?=null)
    //隐藏loading弹窗
    fun hideLoadingDialog()

}