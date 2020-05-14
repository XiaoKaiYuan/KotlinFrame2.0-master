package com.lucas.frame2.module.register

import com.lucas.frame.data.net.RequestCall
import com.lucas.frame.ext.injectExt
import com.lucas.frame2.base.presenter.BaseUserPresenter
import com.lucas.frame2.data.remote.ApiServer
import java.lang.ref.SoftReference

class RegisterPresenter(registerActivity: RegisterActivity) :
    BaseUserPresenter<RegisterActivity>(SoftReference(registerActivity)) {
    private val apiServer: ApiServer by injectExt()

    fun register(username: String, pwd: String) {
        request {
            call = apiServer.register(username, pwd, pwd)
            loadStyle = RequestCall.LoadStyle.DIALOG
        }
    }

    fun login(username: String, pwd: String){
        request {
            call =apiServer.login(username,pwd)
            loadStyle  =RequestCall.LoadStyle.DIALOG
        }
    }
}