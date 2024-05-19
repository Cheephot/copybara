package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDto(
    @Json(name = "username") val email: String,
    @Json(name = "password") val password: String
)
