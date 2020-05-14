package com.lucas.frame.aop.annotation

/**
 * @package    FastClick.kt
 * @author     luan
 * @date       2019-09-02
 * @des        限制快速点击注解，作用域包办view参数的方法  duration ms 点击间隔时间
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class FastClick(val duration: Int = 1000)