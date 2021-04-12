package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent
import com.maxcruz.game.domain.model.Card

sealed class GameIntent : MVIIntent {

    /**
     * Load game information
     */
    data class Load(val sessionId: String, val dealer: Boolean) : GameIntent()

    /**
     * Play card
     */
    data class PlayCard(val sessionId: String, val card: Card): GameIntent()

    /**
     * Report the end of the game to finish
     */
    data class FinishGame(val points: Int? = null): GameIntent()

    /**
     * Close the game session
     */
    object Close: GameIntent()
}