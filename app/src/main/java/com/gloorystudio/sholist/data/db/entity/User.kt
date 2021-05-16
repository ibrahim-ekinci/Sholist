package com.gloorystudio.sholist.data.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User(
    @ColumnInfo(name="id")
    val id: String,
    @ColumnInfo(name="shoppingListId")
    val shoppingListId: String?,
    @ColumnInfo(name="email")
    val email: String?,
    @ColumnInfo(name="verification")
    val verification: Boolean?,
    @ColumnInfo(name="name")
    val name: String?,
    @ColumnInfo(name="username")
    val username: String?,
    @ColumnInfo(name="deviceId")
    val deviceId: String?,
    @ColumnInfo(name="status")
    val status: Boolean?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var dbId = 0
}
