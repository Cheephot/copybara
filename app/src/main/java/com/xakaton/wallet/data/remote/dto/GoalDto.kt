package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoalDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "expectedAmount") val expectedAmount: Double,
    @Json(name = "currentAmount") val currentAmount: Double,
)
