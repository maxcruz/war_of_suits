package com.maxcruz.game.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.maxcruz.game.presentation.mvi.GameIntent

@Composable
fun GameView(
    viewModel: GameViewModel,
    sessionId: String,
) {
    // Load game.
    viewModel.startGame(sessionId)

    // Receive and display view state
    val viewState by remember(viewModel) { viewModel.states() }.collectAsState()
    viewState.Render { intent ->
        when (intent) {
            GameIntent.Close -> viewModel.navigator.actionNavigateUp()
            else -> viewModel.intents().offer(intent)
        }
    }
}
