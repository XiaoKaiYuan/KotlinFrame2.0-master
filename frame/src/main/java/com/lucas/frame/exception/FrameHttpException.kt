package com.lucas.frame.exception

import java.lang.Exception
/**
 * @package    FrameHttpException.kt
 * @author     luan
 * @date       2020-01-07
 * @des        网络层异常
 */
class FrameHttpException(val msg:String): Exception(msg) {

}