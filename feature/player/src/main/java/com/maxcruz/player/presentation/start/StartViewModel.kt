package com.maxcruz.player.presentation.start

import com.maxcruz.core.mvi.MVIViewModel
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartNavigation
import com.maxcruz.player.presentation.start.mvi.StartNavigation.*
import com.maxcruz.player.presentation.start.mvi.StartResult
import com.maxcruz.player.presentation.start.mvi.StartResult.*
import com.maxcruz.player.presentation.start.mvi.StartResult.RecoverGameAttempt.*
import com.maxcruz.player.presentation.start.mvi.StartViewState
import com.maxcruz.player.presentation.start.process.StartProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val processHolder: StartProcessHolder,
) : MVIViewModel<StartIntent, StartViewState, StartResult>(
    initialState = StartViewState(),
    initialIntent = StartIntent.RecoverGame
) {

    override suspend fun transformer(intent: StartIntent): Flow<StartResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(previous: StartViewState, result: StartResult): StartViewState =
        when (result) {
            is RecoverGameAttempt -> reduceRecoverGameAttempt(previous, result)
            is NewGame -> reduceNewGame(previous, result)
            is NavigateToLeaderboard -> navigate(previous, OpenLeaderboard)
        }

    private fun reduceRecoverGameAttempt(
        previous: StartViewState,
        result: RecoverGameAttempt,
    ): StartViewState = when (result) {
        is GameSessionFound -> navigate(previous, OpenStartedGame(result.sessionId))
        is Loading -> previous.copy(isLoading = true, hasError = false)
        is NoGameAvailable -> previous.copy(isLoading = false)
    }

    private fun reduceNewGame(
        previous: StartViewState,
        result: NewGame,
    ): StartViewState = when (result) {
        is NewGame.GameReady -> navigate(previous, OpenNewGame(result.player))
        is NewGame.Loading -> previous.copy(isLoading = true, hasError = false)
        is NewGame.Failure -> previous.copy(isLoading = false, hasError = true)
    }

    private fun navigate(previous: StartViewState, navigation: StartNavigation): StartViewState =
        previous.copy(
            isLoading = false,
            hasError = false,
            navigationQueue = navigation,
        )
}