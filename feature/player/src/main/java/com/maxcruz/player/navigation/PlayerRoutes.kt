package com.maxcruz.player.navigation

object PlayerRoutes {
    const val START = "start"
    const val WAITING = "waiting/{${Arguments.CODE}}"
    const val JOIN = "join"

    object Arguments {
        const val CODE = "code"
    }
}