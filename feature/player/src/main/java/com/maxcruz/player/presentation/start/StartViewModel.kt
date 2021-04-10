package com.maxcruz.player.presentation.start

import com.maxcruz.core.extensions.memoize
import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.player.domain.model.Role
import com.maxcruz.player.navigation.PlayerNavigator
import com.maxcruz.player.presentation.start.mvi.StartIntent
import com.maxcruz.player.presentation.start.mvi.StartResult
import com.maxcruz.player.presentation.start.mvi.StartResult.NewGame
import com.maxcruz.player.presentation.start.mvi.StartResult.RecoverGameAttempt
import com.maxcruz.player.presentation.start.mvi.StartResult.RecoverGameAttempt.*
import com.maxcruz.player.presentation.start.mvi.StartViewState
import com.maxcruz.player.presentation.start.process.StartProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val processHolder: StartProcessHolder,
) : MVIViewModel<StartIntent, StartViewState, StartResult, PlayerNavigator>(
    initialState = StartViewState(),
) {

    override lateinit var navigator: PlayerNavigator

    private val memoizedRecoverGame = { _: Boolean ->
        intents().offer(StartIntent.RecoverGame)
        true
    }.memoize()

    /**
     * Ask for the game code one time when the screen loads
     */
    fun tryRecoverGame() {
        memoizedRecoverGame(true)
    }

    override suspend fun transformer(intent: StartIntent): Flow<StartResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(previous: StartViewState, result: StartResult): StartViewState =
        when (result) {
            is RecoverGameAttempt -> reduceRecoverGameAttempt(previous, result)
            is NewGame -> reduceNewGame(previous, result)
        }

    private fun reduceRecoverGameAttempt(
        previous: StartViewState,
        result: RecoverGameAttempt,
    ): StartViewState = when (result) {
        is Loading -> previous.copy(isLoading = true, hasError = false)
        is NoGameAvailable -> previous.copy(isLoading = false)
        is GameSessionFound -> {
            val dealer = result.player.role == Role.FIRST
            navigator.actionNavigateToGame(result.sessionId, dealer)
            previous
        }
    }

    private fun reduceNewGame(
        previous: StartViewState,
        result: NewGame,
    ): StartViewState = when (result) {
        is NewGame.Loading -> previous.copy(isLoading = true, hasError = false)
        is NewGame.Failure -> previous.copy(isLoading = false, hasError = true)
        is NewGame.JoinToFirstPlayer -> {
            navigator.actionNavigateToJoin()
            previous.copy(isLoading = false, hasError = false)
        }
        is NewGame.WaitForSecondPlayer -> {
            navigator.actionNavigateToWaiting(result.code)
            previous.copy(isLoading = false, hasError = false)
        }
    }
}