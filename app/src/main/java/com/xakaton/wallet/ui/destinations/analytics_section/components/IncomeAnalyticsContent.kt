package com.xakaton.wallet.ui.destinations.analytics_section.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.ui.components.AutoResizeText
import com.xakaton.wallet.ui.components.FontSizeRange
import com.xakaton.wallet.ui.utils.currencyFormatter
import java.math.BigDecimal

@Composable
fun IncomeChart(
    transactions: Map<String, BigDecimal>?,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(Color.White)
        )

        when {
            transactions == null -> {}

            transactions.isEmpty() -> {
                Spacer(modifier = Modifier.height(40.dp))

                EmptyContent()
            }

            else -> {
                NonEmptyContent(transactions = transactions)
            }
        }
    }
}

@Composable
private fun NonEmptyContent(
    transactions: Map<String, BigDecimal>,
) {
    val sum = remember(transactions) { transactions.values.sumOf { it } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
            .background(color = Color.White)
    ) {
        Text(
            text = stringResource(id = R.string.top_spending),
            color = Color(0xFF262626),
            fontSize = 22.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        StatisticsChart(
            chartInfoMap = transactions,
            sum = sum,
        )

        Spacer(modifier = Modifier.height(20.dp))

        transactions.forEach { (type, amount) ->
            val percent = remember(amount, type) { amount * BigDecimal(100) / sum }

            Transaction(
                type = IncomeType.valueOf(type),
                amount = amount,
                percent = percent.toFloat()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Transaction(
    modifier: Modifier = Modifier,
    amount: BigDecimal,
    type: IncomeType,
    percent: Float,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(type.color)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(id = type.textId),
            color = Color(0xFF262626),
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.W400,
            modifier = Modifier.weight(1f, true)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = amount.currencyFormatter(),
                color = Color(0xFF262626),
                fontSize = 18.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.W600
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = "$percent %",
                color = Color(0xFF8589AF),
                fontSize = 14.sp,
                lineHeight = 18.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}

@Composable
private fun StatisticsChart(
    chartInfoMap: Map<String, BigDecimal>,
    sum: BigDecimal,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Chart(
            values = chartInfoMap.values.toList().map { it.toFloat() },
            colors = chartInfoMap.keys.map { IncomeType.valueOf(it).color }
        )

        Sum(sum = sum)
    }
}

@Composable
private fun Sum(
    sum: BigDecimal,
) {
    AutoResizeText(
        text = sum.currencyFormatter(),
        color = Color(0xFF262626),
        fontSizeRange = FontSizeRange(min = 10.sp, max = 20.sp),
        lineHeight = 25.sp,
        fontWeight = FontWeight(600),
        textAlign = TextAlign.Center,
        maxLines = 1,
        modifier = Modifier
            .width(116.dp),
        key = sum
    )
}

@Composable
private fun Chart(
    values: List<Float> = listOf(100f),
    colors: List<Color> = listOf(Color(0xFFF4F4F4)),
) {
    val sumOfValues = remember(values) { values.sum() }

    val proportions = remember(values) { values.map { it * 100 / sumOfValues } }

    val sweepAngles = remember(values) { proportions.map { 360 * it / 100 } }

    Canvas(
        modifier = Modifier
            .size(size = 170.dp)
    ) {
        var startAngle = -90f

        for (i in values.indices) {
            drawArc(
                color = colors[i],
                startAngle = startAngle,
                sweepAngle = sweepAngles[i],
                useCenter = false,
                style = Stroke(width = 18.dp.toPx(), cap = StrokeCap.Butt)
            )
            startAngle += sweepAngles[i]
        }
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = R.drawable.img_statistics,
            contentDescription = null,
            modifier = Modifier.size(280.dp)
        )

        Text(
            text = stringResource(id = R.string.analytics_income_empty_title),
            color = Color(0xFF262626),
            fontSize = 18.sp,
            lineHeight = 26.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.analytics_income_empty_description),
            color = Color(0xFF262626),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )
    }
}