package com.gloorystudio.sholist.model

data class Item(
    val id:String,
    val name:String,
    val count:Int,
    val type:Int,
    val checked:Boolean
)
