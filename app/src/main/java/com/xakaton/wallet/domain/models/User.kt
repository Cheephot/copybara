package com.xakaton.wallet.domain.models

import com.xakaton.wallet.data.remote.dto.UserDto

data class User(
    val id: String,
    val name: String,
    val email: String,
    val budgetId: String
) {
    companion object {
        fun UserDto.mapToUser() = User(
            id = id,
            name = name,
            email = email,
            budgetId = budgetId
        )
    }
}