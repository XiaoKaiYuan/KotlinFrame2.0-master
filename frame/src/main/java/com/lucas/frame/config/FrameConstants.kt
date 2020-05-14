package com.lucas.frame.config

/**
 * @package     com.heid.frame.config
 * @author      lucas
 * @date        2018/11/19
 * @des         框架常量配置
 */
object FrameConstants {
    //网络连接时间
    const val NET_CONN_TIMEOUT = 60L
    //网络读取超时时间
    const val NET_READ_TIMEOUT = 60L
    //网络上传超时时间
    const val NET_WRITE_TIMEOUT = 60L
    //请求缓存大小
    const val HTTP_CACHE_SIZE = 1024 * 1024 * 30L
    //域名
    var HOST =""


    //分页
    object Page {
        //初始页码
        const val START_INDEX = 1
        //每页数据量
        const val PAGE_COUNT = 10
    }

    //网络请求相关
    object NetCode {
        //请求成功码
        const val REQUEST_SUCCESS = 0
        //token过期码
        const val TOKEN_OVERDUE = 1000
        //未登陆
        const val NOT_LOGIN = 5
        //路由错误
        const val RULE_ERROR = 404
    }

    //sp字段名称
    object Sp{
        //cookie
        const val SP_COOKIE = "cookie"
    }
}