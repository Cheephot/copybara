package com.xakaton.wallet.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.xakaton.budget.ui.NavGraphs
import com.xakaton.wallet.ui.nav_graphs.RootNavigator

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun ApplicationRootUI(
    viewModel: ApplicationRootUIViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

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

//    if (viewModel.startDestination != null) {
//        HandleNavigationEvents(
//            navController = navController,
//            navigationEvents = viewModel.navigationEvents
//        )

        ModalBottomSheetLayout(
            bottomSheetNavigator = bottomSheetNavigator,
            sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetBackgroundColor = Color.Transparent,
            sheetElevation = 0.dp,
            modifier = Modifier
        ) {
            DestinationsNavHost(
                modifier = Modifier.fillMaxSize(),
                navGraph = NavGraphs.root,//.copy(startRoute = viewModel.startDestination!!),
                navController = navController,
                engine = rememberAnimatedNavHostEngine(),
                dependenciesContainerBuilder = {
                    dependency(RootNavigator(destinationsNavigator))
                }
            )
        }
//    }
}

//@Composable
//private fun HandleNavigationEvents(
//    navController: NavHostController,
//    navigationEvents: Flow<ApplicationRootUIViewModel.NavigationEvent>,
//) {
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(Unit) {
//        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            withContext(Dispatchers.Main.immediate) {
//                navigationEvents.collect {
//                    navController.navigate(EnterPhoneNumberScreenDestination()) {
//                        popUpTo(NavGraphs.root)
//                    }
//                }
//            }
//        }
//    }
//}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberBottomSheetNavigator(
    sheetState: ModalBottomSheetState
): BottomSheetNavigator {
    return remember(sheetState) {
        BottomSheetNavigator(sheetState = sheetState)
    }
}