package com.xakaton.wallet.data.remote.services

import com.xakaton.wallet.data.remote.dto.JwtDto
import com.xakaton.wallet.data.remote.dto.LoginDto
import com.xakaton.wallet.data.remote.dto.RegisterDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/auth/register")
    suspend fun register(
        @Body request: RegisterDto
    ): JwtDto

    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: LoginDto
    ) : JwtDto

    @POST("/api/v1/auth/refresh")
    suspend fun refreshAccessToken(
        @Body refreshToken: String
    ) : JwtDto
}