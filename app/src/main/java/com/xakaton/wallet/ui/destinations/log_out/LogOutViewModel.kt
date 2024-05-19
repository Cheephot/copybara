package com.xakaton.wallet.ui.destinations.log_out

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.auth.LogOutCommand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogOutViewModel @Inject constructor(
    private val commandDispatcher: CommandDispatcher,
) : ViewModel() {

    var navigationEvent by mutableStateOf<NavigationEvent?>(null)
        private set

    fun onNavigationEventClear() {
        navigationEvent = null
    }

    fun logOut() {
        viewModelScope.launch {
            commandDispatcher.dispatch(LogOutCommand).also {
                navigationEvent = NavigationEvent.NavigateToEnterPhoneNumberScreen
            }
        }
    }

    sealed class NavigationEvent {

        data object NavigateToEnterPhoneNumberScreen : NavigationEvent()
    }
}