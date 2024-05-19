package com.xakaton.wallet.ui.destinations.registration

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.auth.RegisterCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val commandDispatcher: CommandDispatcher,
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

    var name by mutableStateOf("")
        private set

    fun onNameChange(name: String) {
        this.name = name
    }

    var isRequestInProgress by mutableStateOf(false)
        private set

    fun onRegister() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        val job = viewModelScope.launch {
            val result = commandDispatcher.dispatch(
                RegisterCommand(
                    name = name,
                    email = email,
                    password = password
                )
            )

            when (result) {
                is Either.Left -> {
                    Log.d("tag", "error = $result")
                }

                is Either.Right -> {
                    Log.d("tag", "success = $result")
                }
            }
        }

        job.invokeOnCompletion { isRequestInProgress = false }
    }
}