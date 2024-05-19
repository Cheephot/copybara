package com.xakaton.wallet.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.xakaton.wallet.R

enum class GoalsType(@DrawableRes val iconId: Int, @StringRes val textId: Int, val color: Color) {
    REAL_ESTATE(iconId = R.drawable.ic_home, textId = R.string.real_estate, Color(0xFFFEB4E9)),
    TRANSPORT(iconId = R.drawable.ic_transport, textId = R.string.transport, Color(0xFF7FB1FF)),
    DEVELOPMENT(iconId = R.drawable.ic_development, textId = R.string.development, Color(0xFFFFDD43)),
    HEALTH_AND_BEAUTY(iconId = R.drawable.ic_health_and_beauty, textId = R.string.health_and_beauty, Color(0xFFFF9090)),
    ENTERTAINMENT(iconId = R.drawable.ic_entertainment, textId = R.string.entertainment, Color(0xFFBDB4FE)),
    GIFTS(iconId = R.drawable.ic_gifts, textId = R.string.gifts, Color(0xFFFEB4E9)),
    ASSETS(iconId = R.drawable.ic_assets, textId = R.string.money_box, Color(0xFFFFDD43)),
}