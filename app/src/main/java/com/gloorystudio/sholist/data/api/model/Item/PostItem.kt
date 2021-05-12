package com.gloorystudio.sholist.data.api.model.Item

data class PostItem(
    val items: Items,
    val jwt: String,
    val shoppingCardId: String
)