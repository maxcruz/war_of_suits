package com.maxcruz.game.navigation

object GameRoutes {

    object Arguments {
        const val SESSION = "sessionId"
    }

    const val GAME = "game/{${Arguments.SESSION}}"
}