package com.xakaton.wallet.ui.destinations.goals_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.xakaton.wallet.domain.command.CommandDispatcher
import com.xakaton.wallet.domain.command.operations.AddGoalsCommand
import com.xakaton.wallet.domain.models.Goal
import com.xakaton.wallet.domain.models.User
import com.xakaton.wallet.domain.query.QueryDispatcher
import com.xakaton.wallet.domain.query.operations.GetGoalQuery
import com.xakaton.wallet.domain.query.operations.users.GetMeQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class GoalsSectionViewModel @Inject constructor(
    private val commandDispatcher: CommandDispatcher,
    private val queryDispatcher: QueryDispatcher,
) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>?>(null)
    val goals = _goals.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)

    fun addGoals(name: String, expectedAmount: BigDecimal) {
        viewModelScope.launch {
            val result = commandDispatcher.dispatch(
                AddGoalsCommand(
                    userId = _user.value?.id ?: "",
                    expectedAmount = expectedAmount,
                    name = name
                )
            )

            when(result) {
                is Either.Left -> {}
                is Either.Right -> _goals.value = _goals.value?.plus(result.value)
            }
        }
    }

    init {
        viewModelScope.launch {
            queryDispatcher.dispatch(GetMeQuery).collectLatest {
                when (it) {
                    is Either.Left -> {}
                    is Either.Right -> {
                        _user.value = it.value

                        launch {
                            queryDispatcher.dispatch(GetGoalQuery(userId = it.value.id)).collectLatest {
                                when (it) {
                                    is Either.Left -> {}
                                    is Either.Right -> _goals.value = it.value
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}