package com.xakaton.wallet.ui.utils

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency

fun BigDecimal?.currencyFormatter() : String {
    val formatter = NumberFormat.getCurrencyInstance().apply {
        setMaximumFractionDigits(0)
        currency = Currency.getInstance("RUB")
    }

    return formatter.format(this ?: BigDecimal.ZERO)
}