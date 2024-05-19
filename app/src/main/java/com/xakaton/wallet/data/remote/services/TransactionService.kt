package com.xakaton.wallet.data.remote.services

import com.xakaton.wallet.data.remote.dto.AddTransactionRequest
import com.xakaton.wallet.data.remote.dto.TransactionDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TransactionService {
    @POST("/api/v1/transactions/budgets/refill")
    suspend fun addIncomeTransaction(
        @Body request: AddTransactionRequest,
    ): TransactionDto

    @POST("/api/v1/transactions/budgets/debit")
    suspend fun addSpendingTransaction(
        @Body request: AddTransactionRequest,
    ): TransactionDto

    @GET("/api/v1/users/{budgetId}/budgetTransactions/refill")
    suspend fun getIncomeTransactions(
        @Path("budgetId") budgetId: String,
    ): List<TransactionDto>

    @GET("/api/v1/users/{budgetId}/budgetTransactions/debit")
    suspend fun getSpendingTransactions(
        @Path("budgetId") budgetId: String,
    ): List<TransactionDto>
}