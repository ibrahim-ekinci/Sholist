package com.gloorystudio.sholist.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Item(
    @ColumnInfo(name="id")
    val id: String,
    @ColumnInfo(name="shoppingListId")
    val shoppingListId: String?,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="count")
    val count: Int,
    @ColumnInfo(name="checked")
    val checked: Boolean,
    @ColumnInfo(name="img")
    val img: Int
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var dbId = 0
}
