package com.gloorystudio.sholist.data.api.model.auth

data class LoginWithGoogle(
    val deviceId: String?,
    val email: String,
    val staticToken: String
)