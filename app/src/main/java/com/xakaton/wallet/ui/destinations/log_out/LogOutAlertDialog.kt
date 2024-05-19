package com.xakaton.wallet.ui.destinations.log_out

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.xakaton.budget.ui.destinations.LoginScreenDestination
import com.xakaton.budget.ui.destinations.SectionsScreenDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.ui.nav_graphs.RootNavigator
import com.xakaton.wallet.ui.utils.DialogStyle
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate
import com.xakaton.wallet.ui.utils.safePopBackStack

@Composable
@Destination(style = DialogStyle::class)
@RootNavGraph
fun LogOutAlertDialog(
    viewModel: LogOutViewModel = hiltViewModel(),
    rootNavigator: RootNavigator,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LogOutAlertDialog(
        viewModel = viewModel,
        popBackStack = { rootNavigator.safePopBackStack(lifecycle = lifecycleOwner.lifecycle) },
        navigateToEnterPhoneNumberScreen = {
            rootNavigator.launchSingleTopNavigate(
                LoginScreenDestination
            ) {
                popUpTo(SectionsScreenDestination.route) { inclusive = true }
            }
        }
    )
}

@Composable
private fun LogOutAlertDialog(
    viewModel: LogOutViewModel,
    popBackStack: () -> Unit,
    navigateToEnterPhoneNumberScreen: () -> Unit,
) {
    LaunchedNavigationEvent(
        navigationEvent = viewModel.navigationEvent,
        onNavigationEventClear = viewModel::onNavigationEventClear,
        navigateToEnterPhoneNumberScreen = navigateToEnterPhoneNumberScreen
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 52.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.exit_from_account_question),
            color = Color(0xFF262626),
            style = TextStyle(
                fontSize = 17.sp,
                lineHeight = 22.sp,
                fontWeight = FontWeight.W600
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActionsButtons(
            popBackStack = popBackStack,
            logOut = viewModel::logOut
        )
    }
}

@Composable
private fun ActionsButtons(
    modifier: Modifier = Modifier,
    popBackStack: () -> Unit,
    logOut: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TextButton(
            onClick = popBackStack
        ) {
            Text(
                text = stringResource(R.string.cancel),
                color = Color(0xFF262626),
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.W400
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextButton(
            onClick = logOut
        ) {
            Text(
                text = stringResource(R.string.exit),
                color = Color(0xFFFF9090),
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.W600
                )
            )
        }
    }
}

@Composable
private fun LaunchedNavigationEvent(
    navigationEvent: LogOutViewModel.NavigationEvent?,
    onNavigationEventClear: () -> Unit,
    navigateToEnterPhoneNumberScreen: () -> Unit,
) {
    LaunchedEffect(key1 = navigationEvent) {
        when (navigationEvent) {
            is LogOutViewModel.NavigationEvent.NavigateToEnterPhoneNumberScreen -> {
                navigateToEnterPhoneNumberScreen()
            }

            else -> return@LaunchedEffect
        }

        onNavigationEventClear()
    }
}