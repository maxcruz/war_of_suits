package com.maxcruz.player.presentation.waiting.mvi

import com.maxcruz.player.domain.model.Player
import com.maxcruz.core.presentation.mvi.MVIResult

sealed class WaitingResult : MVIResult {

    /**
     * Ask game code result
     */
    data class ShowCode(val code: String) : WaitingResult()

    /**
     * The second player joined and the game can start
     */
    data class GameSession(
        val sessionId: String,
        val player: Player,
    ): WaitingResult()
}