package com.lucas.frame.aop.annotation

/**
 * @package    RequestPermission.kt
 * @author     luan
 * @date       2019-09-05
 * @des        请求权限，拒绝权限引导开启   values 需要请求的权限  isSetup 拒绝后是否开启引导
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.PROPERTY_GETTER
)
annotation class RequestPermission( val values: Array<String>,val isSetup: Boolean = true)