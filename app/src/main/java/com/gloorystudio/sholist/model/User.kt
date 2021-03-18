package com.gloorystudio.sholist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String,
    val name:String,
    val surname:String,
    val username:String,
    val deviceId:String,
    val status:Int
): Parcelable
