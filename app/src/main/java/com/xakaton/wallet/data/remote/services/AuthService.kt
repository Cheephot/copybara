package com.xakaton.wallet.data.remote.services

import com.xakaton.wallet.data.remote.dto.JwtDto
import com.xakaton.wallet.data.remote.dto.RegisterDto
import com.xakaton.wallet.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body request: RegisterDto
    ): UserDto

    @POST("/api/v1/auth/refresh")
    suspend fun refreshAccessToken(
        @Body refreshToken: String
    ) : JwtDto
}