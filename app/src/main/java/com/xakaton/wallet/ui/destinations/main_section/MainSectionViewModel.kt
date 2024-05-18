package com.xakaton.wallet.ui.destinations.main_section

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class MainSectionViewModel @Inject constructor(

) : ViewModel() {
    private val _spendingTransactions = MutableStateFlow<List<BigDecimal>?>(null)
    val spendingTransactions = _spendingTransactions.asStateFlow()

    private val _incomeTransactions = MutableStateFlow<List<BigDecimal>?>(null)
    val incomeTransactions = _incomeTransactions.asStateFlow()

    private val _spendingLimit = MutableStateFlow<BigDecimal?>(null)
    val spendingLimit = _spendingLimit.asStateFlow()

    private val _incomeGoals = MutableStateFlow<BigDecimal?>(null)
    val incomeGoals = _incomeGoals.asStateFlow()
}