package com.gloorystudio.sholist.data.api.model.invitation

data class PostInvitation(
    val jwt: String,
    val shoppingCardId: Int,
    val username: String
)