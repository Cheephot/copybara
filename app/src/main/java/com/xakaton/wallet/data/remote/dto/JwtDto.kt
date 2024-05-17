package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JwtDto(
    @Json(name = "id") val name: String,
    @Json(name = "username") val email: String,
    @Json(name = "accessToken") val accessToken: String,
    @Json(name = "refreshToken") val refreshToken: String,
)
