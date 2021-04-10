package com.maxcruz.game.presentation.mvi

import androidx.compose.runtime.Composable
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Result
import com.maxcruz.game.domain.model.Suit

data class GameViewState(
    val isLoading: Boolean = false,
    val player: String = "",
    val points: Pair<Int, Int> = 0 to 0,
    val deck: List<Card> = emptyList(),
    val playingCard: Card? = null,
    val opponentCard: Card? = null,
    val discard: List<Card> = emptyList(),
    val suitPriority: List<Suit> = emptyList(),
    val result: Result? = null
) : MVIViewState<GameIntent> {

    @Composable
    override fun Render(action: (GameIntent) -> Unit) {

    }

}
