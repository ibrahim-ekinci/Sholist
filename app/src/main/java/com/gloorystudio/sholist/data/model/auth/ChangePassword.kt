package com.gloorystudio.sholist.data.model.auth

data class ChangePassword(
    val jwt: String,
    val newPassword: String,
    val password: String
)