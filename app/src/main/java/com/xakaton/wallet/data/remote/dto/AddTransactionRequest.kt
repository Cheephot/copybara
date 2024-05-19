package com.xakaton.wallet.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddTransactionRequest(
    @Json(name = "type") val type: String,
    @Json(name = "amount") val amount: Double,
    @Json(name = "budgetId") val budgetId: String
)
