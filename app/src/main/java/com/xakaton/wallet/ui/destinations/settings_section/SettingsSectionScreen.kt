package com.xakaton.wallet.ui.destinations.settings_section

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.xakaton.budget.ui.destinations.LogOutAlertDialogDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.domain.models.User
import com.xakaton.wallet.ui.nav_graphs.RootNavigator
import com.xakaton.wallet.ui.nav_graphs.SettingsSectionNavGraph
import com.xakaton.wallet.ui.utils.indicationClickable
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate

@Composable
@Destination
@SettingsSectionNavGraph(start = true)
fun SettingsSectionScreen(
    rootNavigator: RootNavigator,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    SettingsSectionScreen(
        viewModel = viewModel,
        navigateToExitAlertDialog = { rootNavigator.launchSingleTopNavigate(LogOutAlertDialogDestination) }
    )
}

@Composable
private fun SettingsSectionScreen(
    viewModel: SettingsViewModel,
    navigateToExitAlertDialog: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        backgroundColor = Color.White,
        topBar = { TopAppBar() }
    ) { paddingValues ->
        SettingsContent(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navigateToExitAlertDialog = navigateToExitAlertDialog
        )
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel,
    navigateToExitAlertDialog: () -> Unit,
) {
    val user by viewModel.user.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.settings),
            color = Color(0xFF262626),
            fontSize = 30.sp,
            lineHeight = 36.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        UserInfo(user = user)

        DefaultInfo(
            onClick = {},
            textId = R.string.cards,
            iconId = R.drawable.ic_card
        )

        DefaultInfo(
            onClick = navigateToExitAlertDialog,
            textId = R.string.exit,
            iconId = R.drawable.ic_exit
        )
    }
}

@Composable
private fun DefaultInfo(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes textId: Int,
    @DrawableRes iconId: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 68.dp)
            .indicationClickable { onClick() }
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(id = textId),
            color = Color(0xFF262626),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.W400,
        )
    }
}

@Composable
private fun UserInfo(
    modifier: Modifier = Modifier,
    user: User?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 68.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_account),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = user?.name ?: "",
                color = Color(0xFF262626),
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.W400,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = user?.email ?: "",
                color = Color(0xFF8589AF),
                fontSize = 13.sp,
                lineHeight = 14.sp,
                fontWeight = FontWeight.W400,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier,
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.White
        )
    )
}