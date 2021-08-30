package com.gloorystudio.sholist.data.api.model.invitation

data class PatchInvitation(
    val accept: Boolean,
    val invitationId: Int,
    val jwt: String
)