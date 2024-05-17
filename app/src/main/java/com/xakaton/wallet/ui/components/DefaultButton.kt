package com.xakaton.wallet.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    minHeight: Dp = 56.dp,
    bgColor: Color = Color(0xFF262626),
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    contentAlignment: Alignment = Alignment.Center,
    onClick: () -> Unit,
    enabled: Boolean = true,
    horizontalPadding: Dp = 24.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .fillMaxWidth()
            .defaultMinSize(minHeight = minHeight)
            .clip(shape = shape)
            .background(color = bgColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                enabled = enabled
            ) {
                onClick()
            },
        contentAlignment = contentAlignment
    ) {
        content()
    }
}


@Composable
fun DefaultTextButton(
    modifier: Modifier = Modifier,
    minHeight: Dp = 56.dp,
    bgColor: Color = Color(0xFF262626),
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    contentAlignment: Alignment = Alignment.Center,
    enabled: Boolean = true,
    @StringRes textId: Int,
    textColor: Color = Color.White,
    textStyle: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W400
    ),
    textModifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    DefaultButton(
        modifier = modifier,
        minHeight = minHeight,
        bgColor = bgColor,
        shape = shape,
        contentAlignment = contentAlignment,
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = stringResource(id = textId),
            color = textColor,
            style = textStyle,
            modifier = textModifier
        )
    }
}