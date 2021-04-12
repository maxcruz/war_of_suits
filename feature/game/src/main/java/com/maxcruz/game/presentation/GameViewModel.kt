package com.maxcruz.game.presentation

import com.maxcruz.core.extensions.memoize
import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.game.navigation.GameNavigator
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameViewState
import com.maxcruz.game.presentation.process.GameProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val processHolder: GameProcessHolder,
) : MVIViewModel<GameIntent, GameViewState, GameResult, GameNavigator>(
    initialState = GameViewState(isLoading = true),
) {

    override lateinit var navigator: GameNavigator

    private val memoizedStartGame = { load: Pair<String, Boolean> ->
        val (sessionId, dealer) = load
        intents().offer(GameIntent.Load(sessionId, dealer))
    }.memoize()

    /**
     * Prepare the cards to start to play
     */
    fun startGame(sessionId: String, dealer: Boolean) {
        memoizedStartGame(sessionId to dealer)
    }

    override suspend fun transformer(intent: GameIntent): Flow<GameResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(previous: GameViewState, result: GameResult): GameViewState =
        when (result) {
            is GameResult.GameLoad.Loading -> previous.copy(isLoading = true)
            is GameResult.GameLoad.GameReady -> with(result) {
                previous.copy(
                    session = sessionId,
                    isLoading = false,
                    player = hand.player,
                    deck = hand.deck,
                    suitPriority = hand.priority,
                )
            }
            is GameResult.GameLoad.Failure -> {
                // Should report to Crashlytics the start failure
                navigator.actionNavigateUp()
                previous
            }
            is GameResult.Closed -> {
                navigator.actionNavigateUp()
                previous
            }
            is GameResult.Round.Failure -> previous.copy(hasError = true)
            is GameResult.Round.OpponentCard -> previous.copy(opponentCard = result.card)
            is GameResult.Round.RoundResult -> {
                val deck = previous.deck.toMutableList()
                val discard = previous.discard.toMutableList()
                val points = if (result.won) {
                    (previous.points.first + 1) to previous.points.second
                } else {
                    previous.points.first to (previous.points.second + 1)
                }
                previous.copy(deck = deck, discard = discard, points = points, hasError = false)
            }
        }
}