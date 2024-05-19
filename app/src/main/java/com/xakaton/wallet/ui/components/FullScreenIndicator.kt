package com.xakaton.wallet.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun FullscreenProgressIndicator(
    modifier: Modifier = Modifier,
    bgColor: Color = Color.Black.copy(alpha = 0.15f),
) {
    Box(
        modifier = modifier
            .background(bgColor)
            .pointerInput(Unit) {},
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFFDCF42C))
    }
}