package com.lucas.frame.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils

/**
 * @package    DegradeServiceImpl.kt
 * @author     luan
 * @date       2020/3/30
 * @des        全局降级策略
 */
@Route(path = "/service/degrade")
class DegradeServiceImpl : DegradeService {
    override fun onLost(context: Context, postcard: Postcard) {
        ToastUtils.showLong("路由丢失：${postcard.path}")
        //默认跳转到首页
        ARouter.getInstance().build(ARouterPagePath.APP_MAIN).navigation()
    }

    override fun init(context: Context?) {
    }
}