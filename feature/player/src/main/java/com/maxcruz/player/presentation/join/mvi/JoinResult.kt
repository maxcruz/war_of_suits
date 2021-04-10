package com.maxcruz.player.presentation.join.mvi

import com.maxcruz.player.domain.model.Player
import com.maxcruz.core.presentation.mvi.MVIResult

sealed class JoinResult : MVIResult {

    /**
     * Search game session
     */
    sealed class SearchGame : JoinResult() {
        data class Found(
            val sessionId: String,
            val player: Player,
        ) : SearchGame()
        object NotFound: SearchGame()
    }
}