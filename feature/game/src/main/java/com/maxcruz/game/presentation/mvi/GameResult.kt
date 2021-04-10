package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIResult
import com.maxcruz.game.domain.model.Hand

sealed class GameResult : MVIResult {

    sealed class GameLoad : GameResult() {
        object Loading : GameLoad()
        data class GameReady(val hand: Hand) : GameLoad()
        object Failure : GameLoad()
    }

    object Closed: GameResult()
}