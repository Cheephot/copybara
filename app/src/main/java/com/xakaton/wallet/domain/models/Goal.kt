package com.xakaton.wallet.domain.models

import com.xakaton.wallet.data.remote.dto.GoalDto

data class Goal(
    val id: String,
    val name: String,
    val expectedAmount: Double,
    val currentAmount: Double,
) {
    companion object {
        fun GoalDto.mapToGoal() = Goal(
            id = id,
            name = name,
            expectedAmount = expectedAmount,
            currentAmount = currentAmount
        )
    }
}
