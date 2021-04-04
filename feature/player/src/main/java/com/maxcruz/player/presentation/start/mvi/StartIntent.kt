package com.maxcruz.player.presentation.start.mvi

import com.maxcruz.core.mvi.MVIIntent

sealed class StartIntent : MVIIntent {

    /**
     * Try to recover an started game
     */
    object RecoverGame : StartIntent()

    /**
     * Request a new game as the first player
     */
    object CreateGame : StartIntent()

    /**
     * Join to a new game as the second player
     */
    object JoinGame : StartIntent()

    /**
     * Request the leaderboard module navigation
     */
    object RouteToLeaderboard: StartIntent()
}
