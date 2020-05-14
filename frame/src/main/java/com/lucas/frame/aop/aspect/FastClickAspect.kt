package com.lucas.frame.aop.aspect

import android.view.View
import com.lucas.frame.R
import com.lucas.frame.aop.annotation.FastClick
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

/**
 * @package    FastClickAspect.kt
 * @author     luan
 * @date       2019-09-02
 * @des        限制快速点击注解器
 */
@Aspect
class FastClickAspect {
    val TAG = "FastClickAspect"

    //方法切入点
    @Pointcut("execution(@com.lucas.frame.aop.annotation.FastClick * *(..))")
    fun methodAnnotation() {
    }

    //在连接点进行方法替换
    @Around("methodAnnotation()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        val filter = joinPoint.args.filterIsInstance<View>()
        if (filter.isNullOrEmpty())
            throw RuntimeException("FastClick注解只能用于包含View/View子类类参数的方法上.")
        //查找参数表
        val view = filter.first()
        val lastTime = (view.getTag(R.string.frame_fast_click_tag) as? Long) ?: 0L
        val currentTime = System.currentTimeMillis()
        //获取点击间隔时间
        val methodSignature = joinPoint.signature as MethodSignature
//        Log.d("ace","size:${methodSignature.method.annotations.size}")
        if (!methodSignature.method.isAnnotationPresent(FastClick::class.java)) return
        val annotation = methodSignature.method.getAnnotation(FastClick::class.java)
        if (currentTime - lastTime < annotation.duration) {//快速点击
            view.setTag(R.string.frame_fast_click_tag, currentTime)
            return
        }else{
            view.setTag(R.string.frame_fast_click_tag, currentTime)
            //执行原方法
            joinPoint.proceed()
        }

    }
}