package com.gloorystudio.sholist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id:String,
    @SerializedName("email")
    val email:String,
    val verification:Boolean,
    val name:String,
    @SerializedName("username")
    val username:String,
    val deviceId:String,
    val status:Int
): Parcelable
