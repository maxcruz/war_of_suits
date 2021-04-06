package com.maxcruz.player.presentation.waiting.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent

sealed class WaitingIntent : MVIIntent {

    /**
     * Show the code received
     */
    data class Load(val code: String) : WaitingIntent()

    /**
     * Close join screen
     */
    object CloseGame : WaitingIntent()
}