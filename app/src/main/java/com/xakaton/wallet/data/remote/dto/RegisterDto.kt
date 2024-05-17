package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterDto(
    @Json(name = "name") val name: String,
    @Json(name = "username") val email: String,
    @Json(name = "password") val password: String
)
