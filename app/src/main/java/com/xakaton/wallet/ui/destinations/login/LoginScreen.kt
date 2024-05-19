package com.xakaton.wallet.ui.destinations.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.xakaton.budget.ui.destinations.RegistrationScreenDestination
import com.xakaton.wallet.R
import com.xakaton.wallet.ui.components.DefaultTextButton
import com.xakaton.wallet.ui.components.DefaultTextField
import com.xakaton.wallet.ui.components.FullscreenProgressIndicator
import com.xakaton.wallet.ui.components.PasswordTexField
import com.xakaton.wallet.ui.nav_graphs.RootNavigator
import com.xakaton.wallet.ui.utils.launchSingleTopNavigate

@Composable
@Destination
@RootNavGraph
fun LoginScreen(
    rootNavigator: RootNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LoginScreen(
        viewModel = viewModel,
        navigateToRegistrationScreen = {
            rootNavigator.launchSingleTopNavigate(RegistrationScreenDestination)
        }
    )
}

@Composable
private fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToRegistrationScreen: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = { TopAppBar() },
        backgroundColor = Color.White
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            navigateToRegistrationScreen = navigateToRegistrationScreen
        )
    }

    if (viewModel.isRequestInProgress) {
        FullscreenProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.TopAppBar(
        modifier = modifier,
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navigateToRegistrationScreen: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(top = 94.dp)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.enter_in_application),
                color = Color(0xFF262626),
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            DefaultTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                decorationBoxTextId = R.string.email
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTexField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
                decorationBoxTextId = R.string.password
            )
        }

        Buttons(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            onLogin = viewModel::onLogin,
            navigateToRegistrationScreen = navigateToRegistrationScreen
        )
    }
}

@Composable
private fun Buttons(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    navigateToRegistrationScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextButton(
            textId = R.string.enter,
            bgColor = Color(0xFFDCF42C),
            textColor = Color(0xFF262626)
        ) {
            onLogin()
        }

        Spacer(modifier = Modifier.height(12.dp))

        DefaultTextButton(
            textId = R.string.create_account,
        ) {
            navigateToRegistrationScreen()
        }
    }
}