package com.maxcruz.game.presentation.mvi

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.maxcruz.core.presentation.mvi.MVIViewState
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.design.ui.*
import com.maxcruz.game.R
import com.maxcruz.game.domain.model.Card
import com.maxcruz.game.domain.model.Suit
import kotlin.math.roundToInt

data class GameViewState(
    val session: String = "",
    val isLoading: Boolean = false,
    val player: String = "",
    val points: Pair<Int, Int> = 0 to 0,
    val deck: List<Card> = emptyList(),
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
            hasError = hasError,
            isLoading = isLoading,
            closeAlignment = Alignment.TopEnd,
            closeImage = Icons.Default.ExitToApp,
            onClose = {
                action(GameIntent.Close)
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(R.string.game_player_label, player),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                Spacer(modifier = Modifier.size(24.dp))
                MatchScore()
                Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.14f))
                PlayMat {
                    action(GameIntent.PlayCard(session, it))
                }
                Spacer(modifier = Modifier.size(24.dp))
                SuitePriority()
            }
            // Alert
            EndDialog {
                action(GameIntent.FinishGame(it))
            }
        }
    }

    @Composable
    private fun MatchScore() {
        ScoreCard(
            pointsPlayer = points.first,
            pointsOpponent = points.second,
            playerLabel = stringResource(R.string.game_you_label),
            opponentLabel = stringResource(R.string.game_opponent_label),
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun PlayMat(playCard: (Card) -> Unit) {
        BoxWithConstraints {

            // Drag animation
            val swipeState = rememberSwipeableState(0)
            val halfPx = with(LocalDensity.current) { ((maxWidth / 2) - 110.dp).toPx() }
            val startAnchor = with(LocalDensity.current) { -(180.dp).toPx() }
            val anchors = mapOf(startAnchor to 0, halfPx to 1)
            val fraction: Float = try {
                swipeState.progress.fraction
            } catch (e: NoSuchElementException) {
                // Report non-fatal to crashlytics and fallback angle animation
                e.printStackTrace()
                0f
            }
            val angle = if (swipeState.progress.to == 1) {
                (1 - fraction) * 5f
            } else {
                (fraction) * 5f
            }
            val dragged = (!swipeState.isAnimationRunning && swipeState.currentValue == 1)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .swipeable(
                        state = swipeState,
                        enabled = !dragged,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.3f) },
                        orientation = Orientation.Horizontal
                    )
            ) {
                // Deck
                if (deck.isNotEmpty()) {
                    deck.firstOrNull()?.let {
                        SuitCard(
                            value = it.value,
                            suit = it.suit.name,
                            revealed = dragged,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .rotate(angle)
                                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                        )
                        if (dragged) {
                            playCard(it)
                        }
                    }
                }

                // Discard
                discard.lastOrNull()?.let {
                    SuitCard(
                        value = it.value,
                        suit = it.suit.name,
                        revealed = true,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .rotate(-5f)
                            .offset(180.dp)
                    )
                }

                // Opponent
                opponentCard?.let {
                    SuitCard(
                        value = it.value,
                        suit = it.suit.name,
                        elevation = 8.dp,
                        revealed = true,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = 28.dp, y = 28.dp)
                    )
                }
            }
        }
    }

    @Composable
    private fun SuitePriority() {
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

    @Composable
    private fun EndDialog(onDismiss: (Int?) -> Unit) {
        result?.let { result ->
            val title: String
            val text: String
            when (result) {
                Result.Lose -> {
                    title = stringResource(id = R.string.result_lose_title)
                    text = stringResource(id = R.string.result_lose_text)
                }
                Result.Won -> {
                    title = stringResource(id = R.string.result_won_title)
                    text = stringResource(id = R.string.result_won_text)
                }
            }
            val buttonLabel = stringResource(id = R.string.result_button)
            ResultDialog(
                onDismiss = {
                    val points = if (result is Result.Won) points.first else null
                    onDismiss(points)
                },
                title = title,
                text = text,
                buttonLabel = buttonLabel
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
        GameViewState(
            session = "",
            player = "GH123",
            deck = listOf(Card.Ace(Suit.Clubs)),
            discard = listOf(Card.Two(Suit.Spades)),
            suitPriority = listOf(Suit.Clubs, Suit.Diamonds, Suit.Hearts, Suit.Spades),
        ).Render {}
    }
}