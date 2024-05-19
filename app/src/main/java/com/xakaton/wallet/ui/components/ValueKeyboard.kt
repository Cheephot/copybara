package com.xakaton.wallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xakaton.wallet.R
import java.text.DecimalFormat

@Composable
fun ValueKeyboard(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val currencySeparator = remember {
        DecimalFormat().decimalFormatSymbols.decimalSeparator
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        for (rows in 1..4) {
            Row(
                modifier = Modifier
                    .background(Color.White)
            ) {
                for (columns in 1..3) {
                    when (rows) {
                        1 -> Numbers(
                            modifier = Modifier.weight(1f),
                            number = columns,
                            onSumChange = { onValueChange(value + (columns).toString()) }
                        )

                        2 -> Numbers(
                            modifier = Modifier.weight(1f),
                            number = columns + 3,
                            onSumChange = { onValueChange(value + (columns + 3).toString()) }
                        )

                        3 -> Numbers(
                            modifier = Modifier.weight(1f),
                            number = columns + 6,
                            onSumChange = { onValueChange(value + (columns + 6).toString()) }
                        )

                        else -> {
                            when (columns) {
                                1 -> {
                                    Comma(
                                        modifier = Modifier.weight(1f),
                                        currencySeparator = currencySeparator.toString(),
                                        onSumChange = { onValueChange("$value.") }
                                    )
                                }

                                2 -> {
                                    Numbers(
                                        modifier = Modifier.weight(1f),
                                        number = 0,
                                        onSumChange = { onValueChange(value + (0).toString()) }
                                    )
                                }

                                3 -> {
                                    Delete(
                                        modifier = Modifier.weight(1f),
                                        onSumChange = {
                                            if (value.isNotEmpty()) {
                                                onValueChange(
                                                    value.removeSuffix(
                                                        value.last().toString()
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Numbers(
    modifier: Modifier,
    number: Int,
    onSumChange: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(ButtonHeight)
            .border(0.25f.dp, Color(0xFFF4F4F4))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                onSumChange()
            }
    ) {
        Text(
            text = number.toString(),
            color = Color(0xFF262626),
            fontSize = 24.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
private fun Comma(
    onSumChange: () -> Unit,
    currencySeparator: String,
    modifier: Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(ButtonHeight)
            .border(0.25f.dp, Color(0xFFF4F4F4))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                onSumChange()
            }
    ) {
        Text(
            text = currencySeparator,
            color = Color(0xFF262626),
            fontSize = 24.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
private fun Delete(
    onSumChange: () -> Unit,
    modifier: Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(ButtonHeight)
            .border(0.25f.dp, Color(0xFFF4F4F4))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true)
            ) {
                onSumChange()
            }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_keyboard_delete),
            contentDescription = null,
            tint = Color(0xFF262626)
        )
    }
}

private val ButtonHeight = 60.dp