package com.gloorystudio.sholist.model

import android.os.Parcelable
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.User
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ShoppingCard(
    var id: Int,
    var name: String,
    var creatorId: Int,
    var color: Int,
    var userList: ArrayList<User>,
    var itemList: ArrayList<Item>
) : Parcelable