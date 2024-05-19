package com.xakaton.wallet.ui.destinations.main_section.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    categoryType: CategoryType,
) {
    val (imageId, titleId, descriptionId) = remember(categoryType) {
        when (categoryType) {
            CategoryType.SPENDING -> Triple(
                R.drawable.img_spending,
                R.string.title_spending,
                R.string.description_spending
            )

            CategoryType.INCOME -> Triple(
                R.drawable.img_income,
                R.string.title_income,
                R.string.description_income
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = imageId, contentDescription = null, modifier = Modifier.size(280.dp))

        Text(
            text = stringResource(id = titleId),
            color = Color(0xFF262626),
            fontSize = 18.sp,
            lineHeight = 26.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W600
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = descriptionId),
            color = Color(0xFF262626),
            fontSize = 16.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.W400
        )
    }
}