package com.maxcruz.player.presentation.start.mvi

import com.maxcruz.core.mvi.MVINavigation
import com.maxcruz.player.domain.model.Player

sealed class StartNavigation : MVINavigation {

    /**
     * Navigate to the leaderboard module
     */
    object OpenLeaderboard : StartNavigation()

    /**
     * Navigate to open a game as a identified player
     */
    data class OpenNewGame(val player: Player) : StartNavigation()

    /**
     * Navigate an already started game
     */
    data class OpenStartedGame(val sessionId: String) : StartNavigation()
}
