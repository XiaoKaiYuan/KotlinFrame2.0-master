package com.lucas.frame.aop.port

/**
 * @package    IAuthor.kt
 * @author     luan
 * @date       2019-09-05
 * @des        用户认证相关实现接口
 */
interface IAuthor {
    //对应 CheckLogin注解，判断用户是否登陆
    fun isLogin(): Boolean
}