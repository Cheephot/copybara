package com.xakaton.wallet.ui.nav_graphs

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@NavGraph
annotation class SectionsNavGraph(val start: Boolean = false)

class SectionsNavigator(navigator: DestinationsNavigator) : DestinationsNavigator by navigator