package com.lucas.frame.helper

import com.hwangjr.rxbus.Bus
import com.hwangjr.rxbus.annotation.Tag

object AppBus {
    private val bus = Bus()

    fun post(event:Any,tag:String= Tag.DEFAULT){
        bus.post(tag,event)
    }

    fun register(target:Any){
        bus.register(target)
    }

    fun unregister(target:Any){
        bus.unregister(target)
    }
}