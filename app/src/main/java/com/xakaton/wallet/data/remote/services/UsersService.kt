package com.xakaton.wallet.data.remote.services

import com.xakaton.wallet.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersService {

    @GET("/api/v1/users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: String,
    ): UserDto

    @GET("/api/v1/users/me")
    suspend fun getMe(): UserDto
}