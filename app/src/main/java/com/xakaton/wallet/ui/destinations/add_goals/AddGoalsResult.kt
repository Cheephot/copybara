package com.xakaton.wallet.ui.destinations.add_goals

import android.os.Parcelable
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.domain.models.SpendingType
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddGoalsResult(
    val sum: String,
    val name: String
) : Parcelable
