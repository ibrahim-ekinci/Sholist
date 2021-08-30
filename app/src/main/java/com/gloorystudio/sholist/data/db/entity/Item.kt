package com.gloorystudio.sholist.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val id: Int,
    val shoppingListId: Int?,
    val name: String,
    val count: Int,
    var checked: Boolean,
    val img: Int
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var dbId = 0
}
