package com.maxcruz.player.presentation.start.mvi

import com.maxcruz.core.presentation.mvi.MVIResult

sealed class StartResult: MVIResult {

    /**
     * Tells if the application state should or not move to the game screen
     */
    sealed class RecoverGameAttempt: StartResult() {
        object Loading: RecoverGameAttempt()
        data class GameSessionFound(val sessionId: String): RecoverGameAttempt()
        object NoGameAvailable: RecoverGameAttempt()
    }

    /**
     * Result of the attempt to start a new game
     */
    sealed class NewGame: StartResult() {
        object Loading: NewGame()
        data class WaitForSecondPlayer(val code: String): NewGame()
        object JoinToFirstPlayer: NewGame()
        object Failure: NewGame()
    }

    /**
     * Result navigation to Leaderboard
     */
    object NavigateToLeaderboard: StartResult()
}
