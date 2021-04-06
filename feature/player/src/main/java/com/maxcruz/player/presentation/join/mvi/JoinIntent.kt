package com.maxcruz.player.presentation.join.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent

sealed class JoinIntent : MVIIntent {

    /**
     * Enter a game code in the text input
     */
    data class InputCode(val code: String) : JoinIntent()

    /**
     * Discard join to the game
     */
    object CloseGame : JoinIntent()
}