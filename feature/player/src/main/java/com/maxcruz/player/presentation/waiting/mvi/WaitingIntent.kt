package com.maxcruz.player.presentation.waiting.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent

sealed class WaitingIntent : MVIIntent {

    /**
     *
     */
    data class Load(val code: String) : WaitingIntent()

    /**
     * Discard the started game
     */
    object CloseGame : WaitingIntent()
}