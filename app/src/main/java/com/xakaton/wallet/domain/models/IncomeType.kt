package com.xakaton.wallet.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.xakaton.wallet.R

enum class IncomeType(@DrawableRes val iconId: Int, @StringRes val textId: Int, val color: Color) {
    WORK(iconId = R.drawable.ic_work, textId = R.string.work, color = Color(0xFFBDB4FE)),
    BUSINESS(iconId = R.drawable.ic_business, textId = R.string.business, Color(0xFF7FB1FF)),
    FREELANCING(iconId = R.drawable.ic_freelansing, textId = R.string.freelancing , Color(0xFFEEB5F7)),
    ASSETS(iconId = R.drawable.ic_assets, textId = R.string.assets, Color(0xFFFFDD43)),
    GIFTS(iconId = R.drawable.ic_gifts, textId = R.string.gifts, Color(0xFFFEB4E9)),
    REAL_ESTATE(iconId = R.drawable.ic_home, textId = R.string.real_estate, Color(0xFFFEB4E9)),
    SCHOLARSHIP(iconId = R.drawable.ic_scholarship, textId = R.string.scholarship, Color(0xFFDCF42C)),
    PENSION(iconId = R.drawable.ic_pension, textId = R.string.pension, Color(0xFF96F4FE)),
    MISCELLANEOUS(iconId = R.drawable.ic_miscellaneous, textId = R.string.miscellaneous, Color(0xFF8CEB9B)),
}