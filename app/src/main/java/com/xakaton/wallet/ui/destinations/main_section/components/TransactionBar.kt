package com.xakaton.wallet.ui.destinations.main_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xakaton.wallet.domain.models.Transaction
import java.math.BigDecimal

@Composable
fun TransactionBar(transactions: List<Transaction>, colors: Map<String, Color>) {
    val transactionSums = remember(transactions) { transactions.groupAndSumByType() }
    val totalAmount = remember(transactionSums) { transactionSums.values.sumOf { it } }

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        if (transactions.isNotEmpty()) {
            transactionSums.forEach { (type, amount) ->
                val segmentWidthFraction =
                    amount.divide(totalAmount, 10, BigDecimal.ROUND_HALF_UP).toFloat()
                Box(
                    modifier = Modifier
                        .weight(segmentWidthFraction)
                        .fillMaxHeight()
                        .background(colors[type] ?: Color(0xFFF4F4F4))
                )
            }
        }

        if (transactions.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFF4F4F4))
            )
        }
    }
}

private fun List<Transaction>.groupAndSumByType(): Map<String, BigDecimal> {
    return this.groupBy { it.type }.mapValues { entry -> entry.value.sumOf { it.amount } }
}