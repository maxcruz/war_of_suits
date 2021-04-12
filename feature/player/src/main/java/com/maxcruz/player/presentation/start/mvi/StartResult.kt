package com.maxcruz.player.presentation.start.mvi

import com.maxcruz.core.presentation.mvi.MVIResult

sealed class StartResult: MVIResult {

    /**
     * Result of the attempt to start a new game
     */
    sealed class NewGame: StartResult() {
        object Loading: NewGame()
        data class WaitForSecondPlayer(val code: String): NewGame()
        object JoinToFirstPlayer: NewGame()
        object Failure: NewGame()
    }
}
