package com.gloorystudio.sholist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Invitation(
    val id:String,
    val groupName:String,
    val senderUsername:String,
    val date:String
): Parcelable