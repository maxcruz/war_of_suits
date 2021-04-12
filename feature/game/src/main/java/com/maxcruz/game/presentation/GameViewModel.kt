package com.maxcruz.game.presentation

import com.maxcruz.core.extensions.memoize
import com.maxcruz.core.presentation.MVIViewModel
import com.maxcruz.game.navigation.GameNavigator
import com.maxcruz.game.presentation.mvi.GameIntent
import com.maxcruz.game.presentation.mvi.GameResult
import com.maxcruz.game.presentation.mvi.GameResult.*
import com.maxcruz.game.presentation.mvi.GameViewState
import com.maxcruz.game.presentation.process.GameProcessHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val processHolder: GameProcessHolder,
) : MVIViewModel<GameIntent, GameViewState, GameResult, GameNavigator>(
    initialState = GameViewState(isLoading = true),
) {

    override lateinit var navigator: GameNavigator

    private val memoizedStartGame = { sessionId: String ->
        intents().offer(GameIntent.Load(sessionId))
    }.memoize()

    /**
     * Prepare the cards to start to play
     */
    fun startGame(sessionId: String) {
        memoizedStartGame(sessionId)
    }

    override suspend fun transformer(intent: GameIntent): Flow<GameResult> =
        processHolder.processIntent(intent)

    override suspend fun reducer(previous: GameViewState, result: GameResult): GameViewState =
        when (result) {
            is GameLoad -> reduceGameLoad(previous, result)
            is Round -> reduceGameRound(previous, result)
            is GameEnded -> reduceGameEnded(previous, result)
        }

    private fun reduceGameLoad(previous: GameViewState, result: GameLoad): GameViewState =
        when (result) {
            is GameLoad.Loading -> previous.copy(isLoading = true)
            is GameLoad.GameReady -> with(result) {
                previous.copy(
                    session = sessionId,
                    isLoading = false,
                    player = hand.player.takeLast(5).toUpperCase(Locale.getDefault()),
                    deck = hand.deck,
                    suitPriority = hand.priority.reversed(),
                    points = hand.points,
                )
            }
            is GameLoad.Failure -> {
                // Should report to Crashlytics the start failure
                navigator.actionNavigateUp()
                previous
            }
        }

    private fun reduceGameRound(previous: GameViewState, result: Round): GameViewState =
        when (result) {
            is Round.PlayingCard -> previous.copy(cardPlaying = true)
            is Round.Failure -> previous.copy(hasError = true)
            is Round.OpponentCard -> previous.copy(opponentCard = result.card)
            is Round.RoundResult -> {
                val deck = previous.deck.toMutableList()
                val discard = previous.discard.toMutableList()
                deck.remove(result.card)
                discard.add(result.card)
                val first = previous.points.first + result.pair.first
                val second = previous.points.second + result.pair.second
                previous.copy(
                    deck = deck,
                    discard = discard,
                    points = first to second,
                    hasError = false,
                    opponentCard = null,
                    restartingRound = true,
                    gameFinished = deck.isEmpty()
                )
            }
            is Round.RoundRestarted -> {
                previous.copy(restartingRound = false, cardPlaying = false)
            }
        }

    private fun reduceGameEnded(previous: GameViewState, result: GameEnded): GameViewState =
        previous.copy(result = result.result)
}