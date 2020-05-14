package com.lucas.frame.data.net

import android.os.Parcel
import android.os.Parcelable

open class IBean(var errorCode: Int = 0, var errorMsg: String = "") : Parcelable {

    //判断数据是否为空
    open fun isEmpty():Boolean{return false}

    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString()?:""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(errorCode)
        writeString(errorMsg)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<IBean> = object : Parcelable.Creator<IBean> {
            override fun createFromParcel(source: Parcel): IBean = IBean(source)
            override fun newArray(size: Int): Array<IBean?> = arrayOfNulls(size)
        }
    }
}