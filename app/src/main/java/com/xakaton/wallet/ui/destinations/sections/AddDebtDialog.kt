package com.xakaton.wallet.ui.destinations.sections

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xakaton.wallet.R

@Composable
fun AddDebtDialog(
    floatingActionButtonStateChange: (Boolean) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.70f),
                        Color.White.copy(alpha = 0.97f)
                    )
                )
            )
            .clickable(
                onClick = { floatingActionButtonStateChange(false) },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 66.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Transparent)
        ) {
            AddDebtDialogItems(

            )
        }
    }
}

@Composable
private fun AddDebtDialogItems(

) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AddDebtDialogItem(
            color = Color(0xFFFF9090),
            onClick = {

            },
            text = R.string.spending,
            rotateValue = 0f,

            )

        AddDebtDialogItem(
            color = Color(0xFFDCF42C),
            onClick = {

            },
            text = R.string.income,
            rotateValue = 180f,
        )
    }
}

@Composable
private fun AddDebtDialogItem(
    color: Color,
    onClick: () -> Unit,
    @StringRes text: Int,
    rotateValue: Float,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
            .padding(vertical = 30.dp)
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(CircleShape)
                .background(color = color)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_up_arrow),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(rotateValue)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(text),
            color = Color.Black,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W400
        )
    }
}