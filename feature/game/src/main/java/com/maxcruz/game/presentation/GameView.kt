package com.maxcruz.game.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun GameView(
    viewModel: GameViewModel,
    sessionId: String,
    dealer: Boolean,
) {
    // Load game.
    viewModel.startGame(sessionId, dealer)

    // Receive and display view state
    val viewState by remember(viewModel) { viewModel.states() }.collectAsState()
    viewState.Render { intent ->
        viewModel.intents().offer(intent)
    }
}
