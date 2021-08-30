package com.gloorystudio.sholist.data.api.model.Item

data class PostItem(
    val items: List<Items>,
    val jwt: String,
    val shoppingCardId: Int
)