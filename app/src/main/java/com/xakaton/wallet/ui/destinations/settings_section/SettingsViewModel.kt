package com.xakaton.wallet.ui.destinations.settings_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.auth.LogOutCommand
import com.xakaton.wallet.domain.models.User
import com.xakaton.wallet.domain.query.QueryDispatcher
import com.xakaton.wallet.domain.query.operations.users.GetMeQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val queryDispatcher: QueryDispatcher,
    private val commandDispatcher: CommandDispatcher
) : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun logOut() {
        viewModelScope.launch {
            commandDispatcher.dispatch(LogOutCommand)
        }
    }

    init {
        viewModelScope.launch {
            queryDispatcher.dispatch(GetMeQuery).collectLatest {
                when(it) {
                    is Either.Left -> {}
                    is Either.Right -> _user.value = it.value
                }
            }
        }
    }
}