package com.maxcruz.player.presentation.join

import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.player.navigation.PlayerNavigator
import com.maxcruz.player.presentation.join.mvi.JoinIntent
import com.maxcruz.player.presentation.join.mvi.JoinResult
import com.maxcruz.player.presentation.join.mvi.JoinViewState
import com.maxcruz.player.presentation.join.process.JoinProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val processHolder: JoinProcessHolder,
) : MVIViewModel<JoinIntent, JoinViewState, JoinResult, PlayerNavigator>(
    initialState = JoinViewState()
) {

    override lateinit var navigator: PlayerNavigator

    override suspend fun transformer(intent: JoinIntent): Flow<JoinResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(previous: JoinViewState, result: JoinResult): JoinViewState {
        return when (result) {
            is JoinResult.SearchGame.NotFound -> previous.copy(hasError = true)
            is JoinResult.SearchGame.Found -> {
                navigator.actionNavigateToGame(result.sessionId)
                previous.copy(hasError = false)
            }
        }
    }
}
