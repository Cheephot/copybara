package com.xakaton.wallet.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.ramcosta.composedestinations.spec.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class ApplicationRootUIViewModel @Inject constructor(

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
//            combine(
//                queryDispatcher.dispatch(GetShowOnboardingQuery).take(1),
//                queryDispatcher.dispatch(GetSessionDataQuery).take(1),
//                queryDispatcher.dispatch(GetVersionStatusTypeQuery),
//                queryDispatcher.dispatch(GetPersonalProfileQuery)
//            ) { showOnboarding, session, versionStatusType, personalProfile ->
//                Quad(showOnboarding, session, versionStatusType, personalProfile)
//            }.collectLatest { info ->
//                val versionStatusType = when (info.third) {
//                    is Either.Left -> VersionStatusType.UPDATE_NOT_AVAILABLE
//
//                    is Either.Right -> info.third.value
//                }
//
//                val verified = when (info.fourth) {
//                    is Either.Left -> {
//                        if (info.fourth.value is TechnicalError.HttpError) {
//                            (info.fourth.value as TechnicalError.HttpError).statusCode != 403
//                        } else true
//                    }
//
//                    is Either.Right -> true
//                }
//
//                determineNavigationDestination(
//                    showOnboarding = info.first,
//                    sessionIsNotActive = info.second?.refreshToken == null,
//                    verified = verified,
//                    versionStatusType = versionStatusType
//                )
//            }
//        }
//
//        sessionInvalidationEvents.events()
//            .onEach { _navigationEvents.send(NavigationEvent.NavigateToLogin) }
//            .launchIn(viewModelScope)
        }

//    private fun determineNavigationDestination(
//        showOnboarding: Boolean,
//        sessionIsNotActive: Boolean,
//        verified: Boolean,
//        versionStatusType: VersionStatusType
//    ) {
//        startDestination = when {
//            versionStatusType == VersionStatusType.REQUIRED_UPDATE_AVAILABLE -> {
//                UpdateApplicationScreenDestination
//            }
//
//            showOnboarding -> OnboardingScreenDestination
//
//            sessionIsNotActive -> EnterPhoneNumberScreenDestination
//
//            !sessionIsNotActive && !verified -> EnterFullNameScreenDestination
//
//            else -> SectionsScreenDestination
//        }
//    }
    }
}