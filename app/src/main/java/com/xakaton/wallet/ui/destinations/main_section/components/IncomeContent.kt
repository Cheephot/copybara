package com.xakaton.wallet.ui.destinations.main_section.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.ui.destinations.sections.CategoryType
import java.math.BigDecimal

@Composable
fun IncomeContent(
    modifier: Modifier = Modifier,
    transactions: List<BigDecimal>?
) {
    val transactionsStub = listOf<Pair<IncomeType, BigDecimal>>(
        IncomeType.WORK to BigDecimal(160000)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item { HeaderInfo(categoryType = CategoryType.INCOME) }

        item { Spacer(modifier = Modifier.height(4.dp)) }

        when {
            //transactions == null ->  item { {} }

            transactions.isNullOrEmpty() -> {
                item {
                    Spacer(modifier = Modifier.height(48.dp))

                    EmptyContent(categoryType = CategoryType.INCOME)
                }
            }

            else -> {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                items(transactionsStub) { transaction ->
                    Transactions(
                        transactions = transaction
                    )
                }
            }
        }
    }
}

@Composable
private fun Transactions(
    modifier: Modifier = Modifier,
    transactions: Pair<IncomeType, BigDecimal>,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 20.dp)
    ) {

    }
}