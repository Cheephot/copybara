package com.xakaton.wallet.ui.destinations.main_section.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.domain.models.Transaction
import com.xakaton.wallet.ui.destinations.calendar.DateSelection
import com.xakaton.wallet.ui.destinations.calendar.convertRuMonthName
import com.xakaton.wallet.ui.utils.currencyFormatter
import com.xakaton.wallet.ui.utils.indicationClickable
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HeaderInfo(
    modifier: Modifier = Modifier,
    categoryType: CategoryType,
    navigateToCalendarScreen: (DateSelection) -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
    sum: BigDecimal?,
    transactions: List<Transaction>,
    colors: Map<String, Color>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(Color.White)
            .padding(vertical = 24.dp)
    ) {
        Amount(
            categoryType = categoryType,
            navigateToCalendarScreen = {
                navigateToCalendarScreen(DateSelection(startDate = startDate, endDate = endDate))
            },
            startDate = startDate,
            endDate = endDate,
            sum = sum ?: BigDecimal.ZERO
        )

        Spacer(modifier = Modifier.height(18.dp))

        TransactionBar(
            transactions = transactions,
            colors = colors
        )
    }
}

@Composable
private fun Amount(
    modifier: Modifier = Modifier,
    categoryType: CategoryType,
    navigateToCalendarScreen: () -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
    sum: BigDecimal,
) {
    val emptyLimitText = remember(categoryType) {
        when (categoryType) {
            CategoryType.SPENDING -> R.string.without_limit
            CategoryType.INCOME -> R.string.without_goals
        }
    }

    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f, true)
        ) {
            Text(
                text = sum.currencyFormatter(),
                color = Color(0xFF262626),
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.W700
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = emptyLimitText),
                    color = Color(0xFF8589AF),
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.W400
                )

                Spacer(modifier = Modifier.width(6.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.offset(y = 1.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        DateButton(
            navigateToCalendarScreen = navigateToCalendarScreen,
            startDate = startDate,
            endDate = endDate
        )
    }
}

@Composable
private fun DateButton(
    modifier: Modifier = Modifier,
    navigateToCalendarScreen: () -> Unit,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 36.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color(0xFFF4F4F4))
            .indicationClickable { navigateToCalendarScreen() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatDateRange(startDate, endDate),
            color = Color(0xFF8589AF),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .padding(start = 12.dp, top = 6.dp, bottom = 6.dp)
                .offset(y = (-1).dp),
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_down),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 10.dp)
                .offset(y = 1.dp)
        )
    }
}

@Composable
fun formatDateRange(startDate: LocalDate, endDate: LocalDate): String {
    val context = LocalContext.current

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("d MMM", Locale("ru"))

    return when {
        startDate.year == endDate.year && startDate.month == endDate.month && startDate.dayOfMonth == 1 && endDate.dayOfMonth == startDate.lengthOfMonth() -> {
            convertRuMonthName(context, endDate.month.getDisplayName(TextStyle.FULL, Locale("ru")))
        }

        startDate.year == currentDate.year && startDate.month == currentDate.month && startDate.dayOfMonth == 1 && endDate.dayOfMonth == currentDate.dayOfMonth -> {
            convertRuMonthName(context, endDate.month.getDisplayName(TextStyle.FULL, Locale("ru")))
        }

        startDate.year == currentDate.year && startDate.month == currentDate.month && endDate.year == currentDate.year && endDate.month == currentDate.month -> {
            "${startDate.format(formatter)} - ${endDate.format(formatter)}"
        }

        else -> "${startDate.format(formatter)} - ${endDate.format(formatter)}"
    }
}