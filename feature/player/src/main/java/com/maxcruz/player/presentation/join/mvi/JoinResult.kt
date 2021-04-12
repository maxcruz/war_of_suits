package com.maxcruz.player.presentation.join.mvi

import com.maxcruz.core.presentation.mvi.MVIResult

sealed class JoinResult : MVIResult {

    /**
     * Search game session
     */
    sealed class SearchGame : JoinResult() {
        data class Found(val sessionId: String) : SearchGame()
        object NotFound: SearchGame()
    }
}