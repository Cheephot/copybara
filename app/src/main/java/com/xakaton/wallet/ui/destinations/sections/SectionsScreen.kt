package com.xakaton.wallet.ui.destinations.sections

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.xakaton.budget.ui.NavGraph
import com.xakaton.budget.ui.NavGraphs
import com.xakaton.budget.ui.destinations.AddGoalsBottomSheetDestination
import com.xakaton.budget.ui.destinations.AddTransactionBottomSheetDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.CategoryType
import com.xakaton.wallet.ui.nav_graphs.RootNavigator
import com.xakaton.wallet.ui.nav_graphs.SectionsNavigator
import com.xakaton.wallet.ui.rememberBottomSheetNavigator
import com.xakaton.wallet.ui.utils.indicationClickable
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
@Destination
@RootNavGraph(start = true)
fun SectionsScreen(
    rootNavigator: RootNavigator,
) {
    val navController = rememberNavController()
    val currentSection by navController.rememberCurrentSectionNavGraphAsState()

    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        // Значение взято из версии material 1.7.0-alpha03 https://android-review.googlesource.com/c/platform/frameworks/support/+/2852687/6/compose/material/material/src/commonMain/kotlin/androidx/compose/material/ModalBottomSheet.kt#554
        // Можно будет удалить после обновления material до версии 1.7.0
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        )
    )

    val bottomSheetNavigator = rememberBottomSheetNavigator(sheetState = sheetState)
    navController.navigatorProvider += bottomSheetNavigator

    var sectionsNavigator by remember { mutableStateOf<SectionsNavigator?>(null) }

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        modifier = Modifier
    ) {
        SectionsScreen(
            selectedBottomNavigationItem = currentSection.bottomNavigationItem,
            onBottomNavigationItemClick = { bottomNavigationItem ->
                navController.navigate(direction = bottomNavigationItem.sectionNavGraph) {
                    launchSingleTop = true
                    restoreState = true

                    popUpTo(NavGraphs.sections) {
                        saveState = true
                    }
                }
            },
            sectionsNavigator = sectionsNavigator,
            content = { paddingValues ->
                DestinationsNavHost(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .consumeWindowInsets(WindowInsets.navigationBars),
                    navGraph = NavGraphs.sections,
                    navController = navController,
                    dependenciesContainerBuilder = {
                        sectionsNavigator = SectionsNavigator(destinationsNavigator)

                        dependency(rootNavigator)
                        dependency(sectionsNavigator ?: SectionsNavigator(destinationsNavigator))
                    }
                )
            }
        )
    }
}

@Composable
private fun NavigationBar(
    modifier: Modifier = Modifier,
    selectedItem: BottomNavigationItem?,
    onItemClick: (BottomNavigationItem) -> Unit,
) {
    BottomNavigation(
        modifier = modifier
            .consumeWindowInsets(WindowInsets.navigationBars)
            .shadow(elevation = NavigationBarDefaults.Elevation),
        backgroundColor = Color.White
    ) {
        NavigationBarItem(
            activeIconResId = R.drawable.ic_main_active,
            inactiveIconResId = R.drawable.ic_main_inactive,
            isSelected = selectedItem == BottomNavigationItem.Main,
            onClick = { onItemClick(BottomNavigationItem.Main) }
        )

        NavigationBarItem(
            activeIconResId = R.drawable.ic_goals_active,
            inactiveIconResId = R.drawable.ic_goals_inactive,
            isSelected = selectedItem == BottomNavigationItem.Goals,
            onClick = { onItemClick(BottomNavigationItem.Goals) }
        )

        Spacer(modifier = Modifier.weight(1f))

        NavigationBarItem(
            activeIconResId = R.drawable.ic_analytics_active,
            inactiveIconResId = R.drawable.ic_analytics_inactive,
            isSelected = selectedItem == BottomNavigationItem.Analytics,
            onClick = { onItemClick(BottomNavigationItem.Analytics) }
        )

        NavigationBarItem(
            activeIconResId = R.drawable.ic_settings_active,
            inactiveIconResId = R.drawable.ic_settings_inactive,
            isSelected = selectedItem == BottomNavigationItem.Settings,
            onClick = { onItemClick(BottomNavigationItem.Settings) }
        )
    }
}

@Composable
private fun SectionsScreen(
    selectedBottomNavigationItem: BottomNavigationItem?,
    onBottomNavigationItemClick: (BottomNavigationItem) -> Unit,
    sectionsNavigator: SectionsNavigator?,
    content: @Composable (PaddingValues) -> Unit,
) {
    var floatingActionButtonState by remember { mutableStateOf(false) }

    Box {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    selectedItem = selectedBottomNavigationItem,
                    onItemClick = onBottomNavigationItemClick
                )
            },
            content = content
        )

        AnimatedVisibility(
            visible = floatingActionButtonState,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            AddDebtDialog(
                floatingActionButtonStateChange = { floatingActionButtonState = it },
                navigateToAddTransactionBottomSheet = { categoryType: CategoryType ->
                    floatingActionButtonState = false

                    sectionsNavigator?.navigate(AddTransactionBottomSheetDestination(categoryType))
                }
            )
        }

        AddTransactionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(WindowInsets.navigationBars.asPaddingValues())
                .padding(bottom = 12.dp),
            isOpenTransition = updateTransition(
                targetState = floatingActionButtonState,
                label = "rotate_animation"
            ),
            floatingActionButtonState = floatingActionButtonState,
            onClick = {
                if (selectedBottomNavigationItem == BottomNavigationItem.Goals) {
                    sectionsNavigator?.launchSingleTopNavigate(AddGoalsBottomSheetDestination)
                } else {
                    floatingActionButtonState = !floatingActionButtonState
                }
            }
        )
    }
}

@Composable
private fun RowScope.NavigationBarItem(
    @DrawableRes activeIconResId: Int,
    @DrawableRes inactiveIconResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconId = remember(isSelected) { if (isSelected) activeIconResId else inactiveIconResId }

    NavigationBarItem(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        icon = { Icon(painter = painterResource(id = iconId), contentDescription = null) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.Unspecified,
            selectedTextColor = Color.Black,
            unselectedIconColor = Color.Unspecified,
            unselectedTextColor = Color(0xFF8589AF),
            indicatorColor = Color.White
        )
    )
}

@Composable
private fun AddTransactionButton(
    isOpenTransition: Transition<Boolean>,
    floatingActionButtonState: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val degrees by isOpenTransition.animateFloat(label = "Degrees") { targetIsOpen ->
        when (targetIsOpen) {
            true -> 135f
            false -> 0f
        }
    }

    val bgColor: Color by animateColorAsState(
        if (floatingActionButtonState) Color(0xFFF1F1F4) else Color(0xFF262626),
    )

    val buttonColor: Color by animateColorAsState(
        if (floatingActionButtonState) Color(0xFF8589AF) else Color.White,
    )

    Box(
        modifier = modifier
            .size(56.dp)
            .clip(shape = CircleShape)
            .background(color = bgColor)
            .indicationClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            tint = buttonColor,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@Composable
private fun NavHostController.rememberCurrentSectionNavGraphAsState(): State<NavGraph?> {
    val currentBackStackEntry by currentBackStackEntryAsState()

    return remember {
        derivedStateOf {
            currentBackStackEntry?.destination?.hierarchy
                ?.mapNotNull { navDestination -> navDestination.route }
                ?.mapNotNull { route ->
                    NavGraphs.sections.nestedNavGraphs.find { navGraph ->
                        navGraph.route == route
                    }
                }?.singleOrNull()
        }
    }
}

private enum class BottomNavigationItem { Main, Goals, Analytics, Settings }

private val NavGraph?.bottomNavigationItem
    get() = when (this) {
        NavGraphs.mainSection -> BottomNavigationItem.Main
        NavGraphs.goalsSection -> BottomNavigationItem.Goals
        NavGraphs.analyticsSection -> BottomNavigationItem.Analytics
        NavGraphs.settingsSection -> BottomNavigationItem.Settings
        else -> null
    }

private val BottomNavigationItem.sectionNavGraph
    get() = when (this) {
        BottomNavigationItem.Main -> NavGraphs.mainSection
        BottomNavigationItem.Goals -> NavGraphs.goalsSection
        BottomNavigationItem.Analytics -> NavGraphs.analyticsSection
        BottomNavigationItem.Settings -> NavGraphs.settingsSection
    }