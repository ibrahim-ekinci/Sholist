package com.gloorystudio.sholist.data.api.model.invitation

data class DeleteInvitation(
    val jwt: String,
    val shoppingCardId: Int,
    val userId: Int
)