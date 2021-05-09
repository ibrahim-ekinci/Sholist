package com.gloorystudio.sholist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    val id:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("verification")
    val verification:Boolean,
    @SerializedName("name")
    val name:String,
    @SerializedName("username")
    val username:String,
    @SerializedName("deviceId")
    val deviceId:String,
    @SerializedName("status")
    val status:Boolean
): Parcelable
