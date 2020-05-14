package com.lucas.frame.exception

import java.lang.RuntimeException
/**
 * @package    FrameException.kt
 * @author     luan
 * @date       2020-01-07
 * @des        框架异常
 */
class FrameException(var msg:String): RuntimeException(msg) {
}