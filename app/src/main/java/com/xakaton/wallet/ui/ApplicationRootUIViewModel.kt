package com.xakaton.wallet.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.spec.Route
import com.xakaton.budget.ui.destinations.LoginScreenDestination
import com.xakaton.budget.ui.destinations.SectionsScreenDestination
import com.xakaton.wallet.domain.events.SessionInvalidationEvents
import com.xakaton.wallet.domain.query.QueryDispatcher
import com.xakaton.wallet.domain.query.operations.GetSessionDataQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicationRootUIViewModel @Inject constructor(
    private val queryDispatcher: QueryDispatcher,
    sessionInvalidationEvents: SessionInvalidationEvents,
) : ViewModel() {

    sealed interface NavigationEvent {

        data object NavigateToLogin : NavigationEvent
    }

    var startDestination by mutableStateOf<Route?>(null)
        private set

    private val _navigationEvents = Channel<NavigationEvent>()

    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        viewModelScope.launch {
            queryDispatcher.dispatch(GetSessionDataQuery).collectLatest { info ->
                determineNavigationDestination(
                    sessionIsNotActive = info?.refreshToken == null,
                )
            }
        }

        sessionInvalidationEvents.events()
            .onEach { _navigationEvents.send(NavigationEvent.NavigateToLogin) }
            .launchIn(viewModelScope)
    }

    private fun determineNavigationDestination(
        sessionIsNotActive: Boolean,
    ) {
        startDestination = when {
            sessionIsNotActive -> LoginScreenDestination

            else -> SectionsScreenDestination
        }
    }
}