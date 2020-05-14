package com.lucas.frame.aop.aspect

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils
import com.lucas.frame.aop.annotation.RequestPermission
import com.tbruyelle.rxpermissions2.RxPermissions
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * @package    RequestPermissionAspect.kt
 * @author     luan
 * @date       2019-09-05
 * @des        RequestPermission 注解解析器
 */
@Aspect
class RequestPermissionAspect {

    @Pointcut("execution(@com.lucas.frame.aop.annotation.RequestPermission * *(..))")
    fun methodAnnotation() {
    }

    @Around("methodAnnotation()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        //从代理对象中获取activity对象
        var fragmentActivity = joinPoint.`this` as? FragmentActivity
        fragmentActivity = fragmentActivity ?: (joinPoint.`this` as? Fragment)?.activity
        //尝试从参数表里获取activity对象
        if (fragmentActivity == null)
            joinPoint.args.forEach {
                if (it is FragmentActivity) {
                    fragmentActivity = it
                    return@forEach
                }
                if (it is Fragment) {
                    fragmentActivity = it.activity
                    return@forEach
                }
                if (it is View) {
                    val context = it.context
                    if (context is FragmentActivity) {
                        fragmentActivity = context
                        return@forEach
                    }
                    if (context is ContextThemeWrapper) {
                        fragmentActivity = context.baseContext as? FragmentActivity
                        return@forEach
                    }
                }
            }
//        Log.d("ace", "fragmentActivity:$fragmentActivity")
        if (fragmentActivity != null) {
            //获取注解参数
            val methodSignature = joinPoint.signature as MethodSignature
//            Log.d("ace","size:${methodSignature.method.annotations.size}")
            if (!methodSignature.method.isAnnotationPresent(RequestPermission::class.java)) return
            val values = methodSignature.method.getAnnotation(RequestPermission::class.java)?.values
            val isSetup =
                methodSignature.method.getAnnotation(RequestPermission::class.java)?.isSetup
                    ?: false
            if (values.isNullOrEmpty()) {
                throw RuntimeException("@RequestPermission 参数中的 values不能为空!")
            }
            val subscribe = RxPermissions(fragmentActivity!!).request(*values).subscribe {
                if (it) {
                    joinPoint.proceed()
                } else {
                    if (fragmentActivity!!.isDestroyed) {
                        return@subscribe
                    } else {
                        //引导用户开启权限
                        if (isSetup)
                            AlertDialog.Builder(fragmentActivity!!)
                                .setMessage("权限被拒绝,导致部分功能无法正常使用，需要到设置页面手动授权")
                                .setPositiveButton("去授权") { dialog, which ->
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts(
                                        "package",
                                        fragmentActivity!!.packageName,
                                        null
                                    )
                                    intent.data = uri
                                    fragmentActivity!!.startActivity(intent)
                                }
                                .setNegativeButton("取消") { dialog, which ->
                                    ToastUtils.showShort("权限被拒绝,导致部分功能无法正常使用")
                                }.setOnCancelListener {
                                    ToastUtils.showShort("权限被拒绝,导致部分功能无法正常使用")
                                }.show()
                    }
                }
            }


        }

    }
}