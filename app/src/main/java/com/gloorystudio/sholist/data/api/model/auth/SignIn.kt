package com.gloorystudio.sholist.data.api.model.auth

data class SignIn(
    val deviceId: String?,
    val email: String,
    val password: String
)