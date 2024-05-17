package com.xakaton.wallet.domain.models

data class SessionData(
    val accessToken: String? = null,
    val refreshToken: String? = null
)
