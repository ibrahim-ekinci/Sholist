package com.gloorystudio.sholist.data.model.Item

data class PostItem(
    val items: Items,
    val jwt: String,
    val shoppingCardId: String
)