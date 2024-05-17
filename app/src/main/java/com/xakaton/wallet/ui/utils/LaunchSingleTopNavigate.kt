package com.xakaton.wallet.ui.utils

import androidx.navigation.NavOptionsBuilder
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.Direction

fun DestinationsNavigator.launchSingleTopNavigate(
    destination: Direction,
    builder: NavOptionsBuilder.() -> Unit = {}
) {
    navigate(destination) {
        launchSingleTop = true
        builder()
    }
}