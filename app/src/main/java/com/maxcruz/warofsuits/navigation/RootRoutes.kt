package com.maxcruz.warofsuits.navigation

object RootRoutes {
    const val PLAYER = "player_graph"
    const val LEADERBOARD = "leaderboard_graph"
    const val GAME = "game_graph/{${Arguments.SESSION}}"

    object Arguments {
        const val SESSION = "sessionId"
    }
}
