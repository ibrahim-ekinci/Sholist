package com.gloorystudio.sholist.model

import android.os.Parcelable
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.User
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShoppingCard(
    var id: String,
    var name: String,
    var creatorId: String,
    var color: Int,
    var userList: ArrayList<User>,
    var itemList: ArrayList<Item>
) : Parcelable