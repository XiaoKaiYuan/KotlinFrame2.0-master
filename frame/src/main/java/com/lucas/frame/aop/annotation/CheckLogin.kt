package com.lucas.frame.aop.annotation
/**
 * @package    CheckLogin.kt
 * @author     luan
 * @date       2019-09-02
 * @des        检查登陆注解，可作用与方法、字段的get/set方法
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION,AnnotationTarget.PROPERTY_GETTER,AnnotationTarget.PROPERTY_SETTER)
annotation class CheckLogin