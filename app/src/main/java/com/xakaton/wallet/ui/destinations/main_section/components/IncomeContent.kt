package com.xakaton.wallet.ui.destinations.main_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.domain.models.Transaction
import com.xakaton.wallet.ui.destinations.calendar.DateSelection
import com.xakaton.wallet.ui.utils.currencyFormatter
import com.xakaton.wallet.ui.utils.indicationClickable
import java.time.LocalDate

@Composable
fun IncomeContent(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>?,
    navigateToCalendarScreen: (DateSelection) -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val sum = remember(transactions) { transactions?.sumOf { it.amount } }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            HeaderInfo(
                categoryType = CategoryType.INCOME,
                navigateToCalendarScreen = navigateToCalendarScreen,
                startDate = startDate,
                endDate = endDate,
                sum = sum,
                transactions = transactions ?: listOf(),
                colors = IncomeType.entries.associate { it.name to it.color }
            )
        }

        item { Spacer(modifier = Modifier.height(4.dp)) }

        when {
            transactions == null -> item { }

            transactions.isEmpty() -> {
                item {
                    Spacer(modifier = Modifier.height(48.dp))

                    EmptyContent(categoryType = CategoryType.INCOME)
                }
            }

            else -> {
                item { Spacer(modifier = Modifier.height(4.dp)) }

                itemsIndexed(transactions) { index, transaction ->
                    Transaction(
                        transaction = transaction,
                        index = index,
                        lastIndex = transactions.lastIndex,
                        transactions = transactions
                    )
                }
            }
        }
    }
}

@Composable
private fun Transaction(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    index: Int,
    lastIndex: Int,
    transactions: List<Transaction>?,
) {
    val incomeType = remember(transaction) { IncomeType.valueOf(transaction.type) }

    val (topShape, topPadding) = remember(
        index,
        transactions
    ) { if (index == 0) 32.dp to 12.dp else 0.dp to 0.dp }
    val (bottomShape, bottomPadding) = remember(
        index,
        transactions
    ) { if (index == lastIndex) 32.dp to 12.dp else 0.dp to 0.dp }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = topShape,
                    topEnd = topShape,
                    bottomStart = bottomShape,
                    bottomEnd = bottomShape
                )
            )
            .background(Color.White)
            .indicationClickable { }
            .padding(top = topPadding, bottom = bottomPadding)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 72.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(incomeType.color),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = incomeType.iconId),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = stringResource(id = incomeType.textId),
                color = Color(0xFF262626),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.W400,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, true)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Text(
                text = transaction.amount.currencyFormatter(),
                color = Color(0xFF262626),
                fontSize = 18.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.W700
            )
        }

        if (index != lastIndex) {
            Divider(
                thickness = 1.dp,
                color = Color(0xFFF4F4F4),
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}