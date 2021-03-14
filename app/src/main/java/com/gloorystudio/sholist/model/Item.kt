package com.gloorystudio.sholist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id:String,
    val name:String,
    val count:Int,
    val type:Int,
    val checked:Boolean
): Parcelable
