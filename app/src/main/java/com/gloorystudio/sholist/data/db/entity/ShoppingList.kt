package com.gloorystudio.sholist.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingList(
    @ColumnInfo(name = "id")
    var id: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "creatorId")
    var creatorId: String,
    @ColumnInfo(name = "color")
    var color: Int
) {
    @PrimaryKey(autoGenerate = true)
    var dbId = 0

    var dbVersion =0
}