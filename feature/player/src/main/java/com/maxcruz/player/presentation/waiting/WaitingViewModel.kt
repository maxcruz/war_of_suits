package com.maxcruz.player.presentation.waiting

import com.maxcruz.core.extensions.memoize
import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.player.navigation.navigators.GameStartNavigator
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent
import com.maxcruz.player.presentation.waiting.mvi.WaitingResult
import com.maxcruz.player.presentation.waiting.mvi.WaitingViewState
import com.maxcruz.player.presentation.waiting.process.WaitingProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WaitingViewModel @Inject constructor(
    private val processHolder: WaitingProcessHolder,
) : MVIViewModel<WaitingIntent, WaitingViewState, WaitingResult, GameStartNavigator>(
    initialState = WaitingViewState()
) {

    override lateinit var navigator: GameStartNavigator

    private val memoizedRetrieveCode = { code: String ->
        intents().offer(WaitingIntent.Load(code))
        true
    }.memoize()

    /**
     * Ask for the game code one time when the screen loads
     */
    fun load(code: String) {
        memoizedRetrieveCode(code)
    }

    override suspend fun transformer(intent: WaitingIntent): Flow<WaitingResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(
        previous: WaitingViewState,
        result: WaitingResult,
    ): WaitingViewState {
        return when (result) {
            is WaitingResult.ShowCode -> previous.copy(code = result.code)
            is WaitingResult.GameSession -> {
                navigator.actionNavigateToGame(result.sessionId)
                previous
            }
        }
    }
}
