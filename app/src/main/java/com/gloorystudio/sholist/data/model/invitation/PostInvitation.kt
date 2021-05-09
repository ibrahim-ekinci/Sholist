package com.gloorystudio.sholist.data.model.invitation

data class PostInvitation(
    val jwt: String,
    val shoppingCardId: String,
    val username: String
)