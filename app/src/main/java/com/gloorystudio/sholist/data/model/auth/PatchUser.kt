package com.gloorystudio.sholist.data.model.auth

data class PatchUser(
    val jwt: String,
    val newName: String
)