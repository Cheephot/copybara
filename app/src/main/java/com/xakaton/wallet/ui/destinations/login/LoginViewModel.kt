package com.xakaton.wallet.ui.destinations.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.auth.LoginCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val commandDispatcher: CommandDispatcher
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    fun onEmailChange(email: String) {
        this.email = email
    }

    var password by mutableStateOf("")
        private set

    fun onPasswordChange(password: String) {
        this.password = password
    }

    var isRequestInProgress by mutableStateOf(false)
        private set

    fun onLogin() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        val job = viewModelScope.launch {
            commandDispatcher.dispatch(LoginCommand(email = email, password = password))
        }

        job.invokeOnCompletion { isRequestInProgress = false }
    }
}