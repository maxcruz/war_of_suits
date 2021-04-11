package com.maxcruz.game.presentation.mvi

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.ScaffoldPage
import com.maxcruz.design.ui.SuitCard
import com.maxcruz.design.ui.SuitIcon
import com.maxcruz.game.R
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import androidx.compose.material.Card as MaterialCard

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

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.game_player_label, player),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.padding(all = 16.dp))

                MaterialCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.17f)
                        .padding(horizontal = 24.dp)
                ) {
                    Row {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = points.first.toString(),
                                style = MaterialTheme.typography.h2
                            )
                            Text(
                                text = stringResource(R.string.game_you_label),
                                modifier = Modifier.padding(bottom = 16.dp),
                                style = MaterialTheme.typography.body2,
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        ) {
                            Text(
                                text = points.second.toString(),
                                style = MaterialTheme.typography.h2,
                            )
                            Text(
                                text = stringResource(R.string.game_opponent_label),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.85f)
                ) {

                    // Card Deck
                    SuitCard(
                        revealed = false,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .rotate(5f)
                            .offset(-(180.dp))
                    )

                    // Card Playing
                    SuitCard(
                        value = 13,
                        suite = "diamonds",
                        revealed = true,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )

                    // Card 3
                    SuitCard(
                        value = 2,
                        suite = "spades",
                        revealed = true,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .rotate(-5f)
                            .offset(180.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    suitPriority.forEach {
                        SuitIcon(suit = it.name)
                    }
                }

            }
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
        GameViewState(
            player = "GH123",
            suitPriority = listOf(Suit.Clubs, Suit.Diamonds, Suit.Hearts, Suit.Spades)
        ).Render {}
    }
}