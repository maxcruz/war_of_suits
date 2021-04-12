package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent
import com.maxcruz.game.domain.model.Card

sealed class GameIntent : MVIIntent {

    /**
     * Load game information
     */
    data class Load(val sessionId: String) : GameIntent()

    /**
     * Play card
     */
    data class PlayCard(val sessionId: String, val card: Card): GameIntent()

    /**
     * End the turn
     */
    object RoundEnd: GameIntent()

    /**
     * Finish the game
     */
    data class EndGame(val sessionId: String): GameIntent()

    /**
     * Close the game session
     */
    object Close: GameIntent()
}