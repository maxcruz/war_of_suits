package com.maxcruz.player.navigation

object PlayerRoutes {

    object Arguments {

        const val PLAYER_1 = "player1"
        const val PLAYER_2 = "player2"
    }

    const val START = "start"
    const val WAITING = "waiting/{${Arguments.PLAYER_1}}"
    const val JOIN = "join/{${Arguments.PLAYER_2}}"
}