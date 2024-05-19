package com.xakaton.wallet.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.xakaton.wallet.R

enum class SpendingType(@DrawableRes val iconId: Int, @StringRes val textId: Int, val color: Color) {
    SUPERMARKETS(iconId = R.drawable.ic_supermarkets, textId = R.string.supermarkets, Color(0xFFDCF42C)),
    RESTAURANTS(iconId = R.drawable.ic_restaurants, textId = R.string.restaurants, Color(0xFFEEB5F7)),
    TRANSPORT(iconId = R.drawable.ic_transport, textId = R.string.transport, Color(0xFF7FB1FF)),
    HOME(iconId = R.drawable.ic_home, textId = R.string.home, Color(0xFFFEB985)),
    ENTERTAINMENT(iconId = R.drawable.ic_entertainment, textId = R.string.entertainment, Color(0xFFBDB4FE)),
    DEVELOPMENT(iconId = R.drawable.ic_development, textId = R.string.development, Color(0xFFFFDD43)),
    HEALTH_AND_BEAUTY(iconId = R.drawable.ic_health_and_beauty, textId = R.string.health_and_beauty, Color(0xFFFF9090)),
    CLOTHES_AND_SHOES(iconId = R.drawable.ic_clothes_and_shoes, textId = R.string.clothes_and_shoes, Color(0xFF96F4FE)),
    GIFTS(iconId = R.drawable.ic_gifts, textId = R.string.gifts, Color(0xFFFEB4E9)),
    PETS(iconId = R.drawable.ic_animals, textId = R.string.animals, Color(0xFFDAB991)),
    MISCELLANEOUS(iconId = R.drawable.ic_miscellaneous, textId = R.string.miscellaneous, Color(0xFF8CEB9B))
}