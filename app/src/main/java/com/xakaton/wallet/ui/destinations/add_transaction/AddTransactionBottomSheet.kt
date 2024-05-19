package com.xakaton.wallet.ui.destinations.add_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.domain.models.IncomeType
import com.xakaton.wallet.domain.models.SpendingType
import com.xakaton.wallet.ui.components.DefaultTextButton
import com.xakaton.wallet.ui.components.ValueKeyboard
import com.xakaton.wallet.ui.nav_graphs.MainSectionNavGraph
import com.xakaton.wallet.ui.utils.indicationClickable
import com.xakaton.wallet.ui.utils.rememberCurrencyStyleNumberVisualTransformation
import com.xakaton.wallet.ui.utils.textFieldValueRegex
import java.util.Currency

@Composable
@Destination(style = DestinationStyleBottomSheet::class)
@MainSectionNavGraph
fun AddTransactionBottomSheet(
    resultBackNavigator: ResultBackNavigator<AddTransactionResult>,
    categoryType: CategoryType,
) {
    AddTransactionBottomSheet(
        categoryType = categoryType,
        resultNavigateBack = { sum, spendingType, incomeType ->
            resultBackNavigator.navigateBack(
                AddTransactionResult(
                    sum = sum,
                    spendingType = spendingType,
                    incomeType = incomeType
                )
            )
        }
    )
}

@Composable
private fun AddTransactionBottomSheet(
    categoryType: CategoryType,
    resultNavigateBack: (String, SpendingType?, IncomeType?) -> Unit,
) {
    val sumFocusRequester = remember { FocusRequester() }
    val (sum, onSumChange) = remember { mutableStateOf("") }

    val (activeSpendingType, onActiveSpendingTypeChange) = remember(categoryType) {
        val type = if (categoryType == CategoryType.SPENDING) SpendingType.SUPERMARKETS else null
        mutableStateOf(type)
    }

    val (activeIncomeType, onActiveIncomeTypeChange) = remember {
        val type = if (categoryType == CategoryType.INCOME) IncomeType.WORK else null
        mutableStateOf(type)
    }

    val enabledButton = remember(sum) { sum.isNotEmpty() && sum != "0" }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        CompositionLocalProvider(LocalTextInputService.provides(null)) {
            SumTextField(
                sum = sum,
                sumRegexChange = onSumChange,
                keyboardState = true,
                focusRequester = sumFocusRequester
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CategoryTypes(
            categoryType = categoryType,
            activeSpendingType = activeSpendingType,
            activeIncomeType = activeIncomeType,
            onActiveSpendingTypeChange = onActiveSpendingTypeChange,
            onActiveIncomeTypeChange = onActiveIncomeTypeChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        ValueKeyboard(
            value = sum,
            onValueChange = onSumChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        DefaultTextButton(
            textId = when (categoryType) {
                CategoryType.SPENDING -> R.string.add_spending
                CategoryType.INCOME -> R.string.add_income
            },
            bgColor = if (enabledButton) Color(0xFFDCF42C) else Color(0xFFF4F4F4),
            textColor = if (enabledButton) Color(0xFF262626) else Color(0xFF262626).copy(alpha = 0.30f),
            enabled = enabledButton,
            onClick = { resultNavigateBack(sum, activeSpendingType, activeIncomeType) }
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun CategoryTypes(
    categoryType: CategoryType,
    activeSpendingType: SpendingType?,
    activeIncomeType: IncomeType?,
    onActiveSpendingTypeChange: (SpendingType) -> Unit,
    onActiveIncomeTypeChange: (IncomeType) -> Unit,
) {
    LazyRow {
        item { Spacer(modifier = Modifier.width(16.dp)) }

        when (categoryType) {
            CategoryType.SPENDING -> {
                items(SpendingType.entries.toTypedArray()) { item ->
                    SpendingItem(
                        spendingType = item,
                        isActive = activeSpendingType == item
                    ) {
                        onActiveSpendingTypeChange(item)
                    }

                    if (item != SpendingType.MISCELLANEOUS) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            CategoryType.INCOME -> {
                items(IncomeType.entries.toTypedArray()) { item ->
                    IncomeItem(
                        incomeType = item,
                        isActive = activeIncomeType == item
                    ) {
                        onActiveIncomeTypeChange(item)
                    }

                    if (item != IncomeType.MISCELLANEOUS) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.width(16.dp)) }
    }
}

@Composable
private fun SpendingItem(
    spendingType: SpendingType,
    isActive: Boolean,
    onActiveSpendingTypeChange: () -> Unit,
) {
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 44.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                1.dp,
                if (!isActive) Color(0xFFF4F4F4) else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .background(color = if (isActive) Color(0xFF262626) else Color.Transparent)
            .indicationClickable { onActiveSpendingTypeChange() }
            .padding(start = 12.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = spendingType.iconId),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = spendingType.textId),
            color = if (!isActive) Color(0xFF262626) else Color.White,
            fontSize = 13.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
private fun IncomeItem(
    incomeType: IncomeType,
    isActive: Boolean,
    onActiveIncomeTypeChange: () -> Unit,
) {
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 44.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                1.dp,
                if (!isActive) Color(0xFFF4F4F4) else Color.Transparent,
                RoundedCornerShape(20.dp)
            )
            .background(color = if (isActive) Color(0xFF262626) else Color.Transparent)
            .indicationClickable { onActiveIncomeTypeChange() }
            .padding(start = 12.dp, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = incomeType.iconId),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = stringResource(id = incomeType.textId),
            color = if (!isActive) Color(0xFF262626) else Color.White,
            fontSize = 13.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.W400
        )
    }
}

@Composable
fun SumTextField(
    sum: String,
    sumRegexChange: (String) -> Unit,
    keyboardState: Boolean,
    focusRequester: FocusRequester,
) {
    LaunchedEffect(key1 = keyboardState) {
        if (keyboardState) {
            focusRequester.requestFocus()
        }
    }

    val sumTextFieldValue by remember(sum) { mutableStateOf(TextFieldValue(sum.ifEmpty { "0" })) }

    Box(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
            .height(60.dp)
    ) {
        BasicTextField(
            value = sumTextFieldValue.copy(selection = TextRange(sum.length)),
            onValueChange = { sumRegexChange(textFieldValueRegex(it.text)) },
            textStyle = TextStyle(
                color = if (sum.isEmpty()) Color(0xFF8589AF) else Color(0xFF262626),
                fontSize = 38.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.W500,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            maxLines = 1,
            singleLine = true,
            cursorBrush = SolidColor(Color(0xFF262626)),
            visualTransformation = rememberCurrencyStyleNumberVisualTransformation(
                currency = Currency.getInstance("RUB"),
                transformText = { AnnotatedString(it) }
            ),
            modifier = Modifier
                .focusRequester(focusRequester)
                .align(Alignment.CenterStart)
        )
    }
}
