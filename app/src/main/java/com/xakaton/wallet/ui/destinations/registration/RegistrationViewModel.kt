package com.xakaton.wallet.ui.destinations.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor() : ViewModel() {
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
}