package com.xakaton.wallet.ui.destinations.add_transaction

import android.os.Parcelable
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.domain.models.SpendingType
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddTransactionResult(
    val sum: String,
    val spendingType: SpendingType? = null,
    val incomeType: IncomeType? = null
) : Parcelable
