package com.lucas.frame2.module.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lucas.frame.base.view.activity.BaseNetActivity
import com.lucas.frame.data.net.IBean
import com.lucas.frame.data.net.RequestCall
import com.lucas.frame2.R
import com.lucas.frame2.data.bean.RegisterBean
import com.lucas.frame2.module.project.ProjectListActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * @package    RegisterActivity.kt
 * @author     luan
 * @date       2020-01-07
 * @des        注册
 */
class RegisterActivity : BaseNetActivity<RegisterPresenter>(), View.OnClickListener {
    companion object {
        val request_code = 100
        fun launch(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivityForResult(intent, request_code)
        }
    }

    override val mPresenter: RegisterPresenter by inject { parametersOf(this) }
    override val layoutId: Int = R.layout.activity_register

    override fun initView(savedInstanceState: Bundle?) {
        arrayOf(v_register,v_login).addClickListener(this)
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onClick(v: View) {
        when (v) {
            v_register -> {
                mPresenter.register(v_phone.text.toString(), v_pwd.text.toString())
            }
            v_login -> {
                mPresenter.login(v_phone.text.toString(), v_pwd.text.toString())
            }
        }
    }

    override fun onRequestSuccess(
        bean: IBean,
        requestTag: String,
        requestListModel: RequestCall.RequestListModel
    ) {
        if (bean is RegisterBean){
            "注册成功".toast()
        }else{
            "登陆成功".toast()
            ProjectListActivity.launch(this)
        }
    }

    override fun onRequestFail(
        bean: IBean,
        requestTag: String,
        requestListModel: RequestCall.RequestListModel
    ) {
        super.onRequestFail(bean, requestTag, requestListModel)
    }



}
