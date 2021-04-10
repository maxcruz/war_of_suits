package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIResult
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit

sealed class GameResult : MVIResult {

    sealed class GameLoad : GameResult() {
        data class GameReady(
            val player: String,
            val deck: List<Card>,
            val priority: List<Suit>,
        ) : GameLoad()
        object Failure : GameLoad()
    }

}