package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent

sealed class GameIntent : MVIIntent {

    /**
     * Load game information
     */
    data class Load(val sessionId: String, val dealer: Boolean) : GameIntent()

    /**
     * Close the game session
     */
    object Close: GameIntent()
}