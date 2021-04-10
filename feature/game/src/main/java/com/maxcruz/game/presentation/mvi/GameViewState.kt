package com.maxcruz.game.presentation.mvi

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.game.R
import com.maxcruz.game.domain.model.Card
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
    val result: Result? = null,
    val hasError: Boolean = false,
) : MVIViewState<GameIntent> {

    @Composable
    override fun Render(action: (GameIntent) -> Unit) {
        ScaffoldPage(
            errorMessage = R.string.game_error_message,
            closeAlignment = Alignment.TopEnd,
            closeImage = Icons.Default.ExitToApp,
            onClose = {
                action(GameIntent.Close)
            }
        ) {
            Text(
                text = "Player: $player",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }
    }

    sealed class Result {
        object Won : Result()
        object Lose : Result()
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        GameViewState(player = "GH123").Render {}
    }
}