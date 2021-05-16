package com.gloorystudio.sholist.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Invitation(
    val id:String,
    val shoppingCardId:String,
    val groupName:String,
    val senderUsername:String,
    val date:String
): Parcelable