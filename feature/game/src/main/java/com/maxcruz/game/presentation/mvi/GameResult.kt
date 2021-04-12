package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIResult
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Hand

sealed class GameResult : MVIResult {

    sealed class GameLoad : GameResult() {
        object Loading : GameLoad()
        data class GameReady(val sessionId: String, val hand: Hand) : GameLoad()
        object Failure : GameLoad()
    }

    sealed class Round : GameResult() {
        data class OpponentCard(val card: Card): Round()
        data class RoundResult(val won: Boolean): Round()
        object Failure : Round()
    }

    object Closed: GameResult()
}