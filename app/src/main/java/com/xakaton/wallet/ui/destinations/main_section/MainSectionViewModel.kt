package com.xakaton.wallet.ui.destinations.main_section

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.computations.result
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.transactions.AddIncomeTransactionCommand
import com.xakaton.wallet.domain.command.operations.transactions.AddSpendingTransactionCommand
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.domain.models.SpendingType
import com.xakaton.wallet.domain.models.Transaction
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
class MainSectionViewModel @Inject constructor(
    private val queryDispatcher: QueryDispatcher,
    private val commandDispatcher: CommandDispatcher,
) : ViewModel() {
    private val _spendingTransactions = MutableStateFlow<List<Transaction>?>(null)
    val spendingTransactions = _spendingTransactions.asStateFlow()

    private val _incomeTransactions = MutableStateFlow<List<Transaction>?>(null)
    val incomeTransactions = _incomeTransactions.asStateFlow()

    private val _spendingLimit = MutableStateFlow<BigDecimal?>(null)
    val spendingLimit = _spendingLimit.asStateFlow()

    private val _incomeGoals = MutableStateFlow<BigDecimal?>(null)
    val incomeGoals = _incomeGoals.asStateFlow()

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

    private val _user = MutableStateFlow<User?>(null)

    fun addSpendingTransaction(sum: BigDecimal, spendingType: SpendingType) {
        viewModelScope.launch {
            val result = commandDispatcher.dispatch(
                AddSpendingTransactionCommand(
                    amount = sum,
                    type = spendingType,
                    budgetId = _user.value?.budgetId ?: ""
                )
            )

            when (result) {
                is Either.Left -> Log.d("tag", "AddSpendingTransactionCommand = ${result.value}")
                is Either.Right -> _spendingTransactions.value =
                    _spendingTransactions.value?.plus(result.value)
            }
        }
    }

    fun addIncomeTransaction(sum: BigDecimal, incomeType: IncomeType) {
        viewModelScope.launch {
            val result = commandDispatcher.dispatch(
                AddIncomeTransactionCommand(
                    amount = sum,
                    type = incomeType,
                    budgetId = _user.value?.budgetId ?: ""
                )
            )

            when (result) {
                is Either.Left -> Log.d("tag", "AddIncomeTransactionCommand = ${result.value}")
                is Either.Right -> _incomeTransactions.value =
                    _incomeTransactions.value?.plus(result.value)
            }
        }
    }


    init {
        viewModelScope.launch {
            queryDispatcher.dispatch(GetMeQuery).collectLatest {
                when (it) {
                    is Either.Left -> Log.d("tag", "GetMeQuery error = ${it.value}")
                    is Either.Right -> {
                        _user.value = it.value

                        launch {
                            queryDispatcher.dispatch(
                                GetSpendingTransactionsQuery(
                                    budgetId = _user.value?.budgetId ?: ""
                                )
                            ).collectLatest {
                                when (it) {
                                    is Either.Left -> Log.d("tag", "GetSpendingTransactionsQuery error = ${it.value}")
                                    is Either.Right -> _spendingTransactions.value = it.value
                                }
                            }
                        }

                        launch {
                            queryDispatcher.dispatch(
                                GetIncomeTransactionsQuery(
                                    budgetId = _user.value?.budgetId ?: ""
                                )
                            ).collectLatest {
                                when (it) {
                                    is Either.Left -> Log.d("tag", "GetIncomeTransactionsQuery error = ${it.value}")
                                    is Either.Right -> _incomeTransactions.value = it.value
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}