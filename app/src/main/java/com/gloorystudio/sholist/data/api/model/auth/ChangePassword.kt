package com.gloorystudio.sholist.data.api.model.auth

data class ChangePassword(
    val jwt: String,
    val newPassword: String,
    val password: String
)