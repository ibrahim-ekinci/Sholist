package com.gloorystudio.sholist.data.model.auth

data class SignIn(
    val deviceId: String,
    val email: String,
    val password: String
)