package com.gloorystudio.sholist.data.model.invitation

data class DeleteInvitation(
    val jwt: String,
    val shoppingCardId: String,
    val userId: String
)