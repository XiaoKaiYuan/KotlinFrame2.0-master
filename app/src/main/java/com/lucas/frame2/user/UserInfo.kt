package com.lucas.frame2.user

import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.lucas.frame.ext.injectExt

/**
 * @package    UserInfo.kt
 * @author     luan
 * @date       2019-12-25
 * @des        当前用户缓存的信息
 */
object UserInfo {
    private const val SP_USER = "sp_user"
    var userBean = UserBean()

    private val gson by injectExt<Gson>()

    init {
        //读取缓存数据
        val userJson = SPUtils.getInstance().getString(SP_USER)
        if (userJson.isNotEmpty()) {
            userBean = gson.fromJson(userJson, UserBean::class.java)
        }
    }

    //更新缓存
    fun refreshUserBean(userBean: UserBean) {
        this.userBean = userBean
        SPUtils.getInstance().put(SP_USER, gson.toJson(userBean))
    }

    //退出登陆
    fun logout() {
        //清除缓存
        this.userBean = UserBean()
        SPUtils.getInstance().remove(SP_USER)
    }
}