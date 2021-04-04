package com.maxcruz.player.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxcruz.core.mvi.MVIViewModel
import com.maxcruz.player.presentation.start.StartNavigation.*
import com.maxcruz.player.presentation.start.StartResult.*
import com.maxcruz.player.presentation.start.StartResult.RecoverGameAttempt.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val processHolder: StartProcessHolder,
) : ViewModel(), MVIViewModel<StartIntent, StartViewState> {

    private val intentChannel = Channel<StartIntent>(Channel.UNLIMITED)
    private val initialState = StartViewState()
    private val stateChannel: StateFlow<StartViewState> = intentChannel
        .receiveAsFlow()
        .onStart { emit(StartIntent.RecoverGame) }
        .flatMapMerge(transform = processHolder::processIntent)
        .scan(initialState, ::reducer)
        .stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    override fun intents(): Channel<StartIntent> = intentChannel

    override fun states(): StateFlow<StartViewState> = stateChannel

    private fun reducer(previous: StartViewState, result: StartResult): StartViewState {
        return when (result) {
            is RecoverGameAttempt -> reduceRecoverGameAttempt(previous, result)
            is NewGame -> reduceNewGame(previous, result)
            is NavigateToLeaderboard -> navigate(previous, OpenLeaderboard)
        }
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