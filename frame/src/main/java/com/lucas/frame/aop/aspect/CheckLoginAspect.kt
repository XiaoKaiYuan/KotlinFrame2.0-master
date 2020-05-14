package com.lucas.frame.aop.aspect

import com.lucas.frame.config.FrameInitConfig
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.lang.RuntimeException

/**
 * @package    CheckLoginAspect.kt
 * @author     luan
 * @date       2019-09-02
 * @des        检查登陆注解器
 */
@Aspect
class CheckLoginAspect {

    @Pointcut("execution(@com.lucas.frame.aop.annotation.CheckLogin * *(..))")
    fun methodAnnotation() {
    }

    @Around("methodAnnotation()")
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        if (FrameInitConfig.getAuthorImp()==null){
            throw RuntimeException("请先配置FrameInitHelper.setAuthorImp()")
        }
        if (FrameInitConfig.getAuthorImp()?.isLogin() == true) {
            joinPoint.proceed()
        }
    }
}