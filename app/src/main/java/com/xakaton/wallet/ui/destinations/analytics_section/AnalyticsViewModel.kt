package com.xakaton.wallet.ui.destinations.analytics_section

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.xakaton.wallet.domain.models.User
import com.xakaton.wallet.domain.query.QueryDispatcher
import com.xakaton.wallet.domain.query.operations.transactions.GetIncomeTransactionsQuery
import com.xakaton.wallet.domain.query.operations.transactions.GetSpendingTransactionsQuery
import com.xakaton.wallet.domain.query.operations.users.GetMeQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val queryDispatcher: QueryDispatcher,
) : ViewModel() {
    private val _spendingTransactions = MutableStateFlow<Map<String, BigDecimal>?>(null)
    val spendingTransactions = _spendingTransactions.asStateFlow()

    private val _incomeTransactions = MutableStateFlow<Map<String, BigDecimal>?>(null)
    val incomeTransactions = _incomeTransactions.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)

    var startDate by mutableStateOf(LocalDate.of(LocalDate.now().year, LocalDate.now().month, 1))
        private set

    fun onStartDateChange(startDate: LocalDate) {
        this.startDate = startDate
    }

    var endDate by mutableStateOf(LocalDate.now())
        private set

    fun onEndDateChange(endDate: LocalDate) {
        this.endDate = endDate
    }

    fun getTransaction() {
        viewModelScope.launch {
            queryDispatcher.dispatch(GetMeQuery).collectLatest {
                when (it) {
                    is Either.Left -> Log.d("tag", "GetMeQuery error = ${it.value}")
                    is Either.Right -> {
                        _user.value = it.value

                        viewModelScope.launch {
                            queryDispatcher.dispatch(
                                GetSpendingTransactionsQuery(
                                    budgetId = _user.value?.budgetId ?: ""
                                )
                            ).collectLatest {
                                when (it) {
                                    is Either.Left -> Log.d(
                                        "tag",
                                        "GetSpendingTransactionsQuery error = ${it.value}"
                                    )

                                    is Either.Right -> {
                                        _spendingTransactions.value = it.value
                                            .filter { it.createdAt in startDate..endDate }
                                            .groupBy { it.type }
                                            .mapValues { entry -> entry.value.sumOf { it.amount } }
                                    }
                                }
                            }
                        }

                        viewModelScope.launch {
                            queryDispatcher.dispatch(
                                GetIncomeTransactionsQuery(
                                    budgetId = _user.value?.budgetId ?: ""
                                )
                            ).collectLatest {
                                when (it) {
                                    is Either.Left -> Log.d(
                                        "tag",
                                        "GetIncomeTransactionsQuery error = ${it.value}"
                                    )

                                    is Either.Right -> {
                                        _incomeTransactions.value = it.value
                                            .filter { it.createdAt in startDate..endDate }
                                            .groupBy { it.type }
                                            .mapValues { entry -> entry.value.sumOf { it.amount } }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}