package com.maxcruz.player.presentation.start

import com.maxcruz.core.mvi.MVIResult
import com.maxcruz.player.domain.model.Player

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
        data class GameReady(val player: Player): NewGame()
        object Failure: NewGame()
    }

    object NavigateToLeaderboard: StartResult()
}
