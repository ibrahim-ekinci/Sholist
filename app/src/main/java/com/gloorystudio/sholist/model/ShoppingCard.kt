package com.gloorystudio.sholist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ShoppingCard (
            var id:String,
            var name:String,
            var creatorId:String,
            var color:Int,
            var userList:ArrayList<User>,
            var itemList:ArrayList<Item>
            //var tokenType: @RawValue Any? = null
        ) : Parcelable
