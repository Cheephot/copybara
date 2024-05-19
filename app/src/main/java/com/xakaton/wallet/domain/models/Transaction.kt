package com.xakaton.wallet.domain.models

import com.xakaton.wallet.data.remote.dto.TransactionDto
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Transaction(
    val id: String,
    val type: String,
    val amount: BigDecimal,
    val budgetId: String,
    val createdAt: LocalDate
) {
    companion object {
        fun TransactionDto.mapToTransaction() = Transaction(
            id = id,
            type = type,
            amount = amount.toBigDecimal(),
            budgetId = budgetId,
            createdAt = stringToLocalDate(createdAt)
        )

        private fun stringToLocalDate(dateString: String): LocalDate {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(dateString, formatter)
        }
    }
}
