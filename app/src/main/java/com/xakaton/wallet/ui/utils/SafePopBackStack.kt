package com.xakaton.wallet.ui.utils

import androidx.lifecycle.Lifecycle
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

fun DestinationsNavigator.safePopBackStack(
    lifecycle: Lifecycle
) {
    if (lifecycle.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}