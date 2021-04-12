package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIResult
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Hand
import com.maxcruz.game.domain.model.Result

sealed class GameResult : MVIResult {

    sealed class GameLoad : GameResult() {
        object Loading : GameLoad()
        data class GameReady(val sessionId: String, val hand: Hand) : GameLoad()
        object Failure : GameLoad()
    }

    sealed class Round : GameResult() {
        object PlayingCard: Round()
        data class OpponentCard(val card: Card): Round()
        data class RoundResult(val pair: Pair<Int, Int>, val card: Card): Round()
        object RoundRestarted: Round()
        object Failure : Round()
    }

    data class GameEnded(val result: Result) : GameResult()
}