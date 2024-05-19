package com.xakaton.wallet.data.remote.services

import com.xakaton.wallet.data.remote.dto.AddGoalsRequest
import com.xakaton.wallet.data.remote.dto.GoalDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoalsService {
    @POST("/api/v1/users/{userId}/goals")
    suspend fun addGoal(
        @Path("userId") userId: String,
        @Body request: AddGoalsRequest,
    ): GoalDto

    @GET("/api/v1/users/{userId}/goals")
    suspend fun getGoals(
        @Path("userId") userId: String,
    ): List<GoalDto>
}