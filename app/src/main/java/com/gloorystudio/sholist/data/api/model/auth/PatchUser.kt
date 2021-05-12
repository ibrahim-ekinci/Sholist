package com.gloorystudio.sholist.data.api.model.auth

data class PatchUser(
    val jwt: String,
    val newName: String
)