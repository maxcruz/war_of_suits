package com.maxcruz.game.presentation.mvi

import com.maxcruz.core.presentation.mvi.MVIIntent
import com.maxcruz.core.domain.model.Player

sealed class GameIntent : MVIIntent {
    data class Load(val sessionId: String, val player: Player) : GameIntent()
}