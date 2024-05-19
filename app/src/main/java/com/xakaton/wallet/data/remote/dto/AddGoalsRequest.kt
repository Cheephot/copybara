package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddGoalsRequest(
    @Json(name = "name") val name: String,
    @Json(name = "expectedAmount") val expectedAmount: Double
)