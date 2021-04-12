package com.maxcruz.player.presentation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.maxcruz.player.presentation.start.mvi.StartIntent

@Composable
fun StartView(viewModel: StartViewModel) {
    // Receive and display the view state. Process user intents and navigation intents
    val viewState by remember(viewModel) { viewModel.states() }.collectAsState()
    viewState.Render { intent ->
        when (intent) {
            is StartIntent.RouteToLeaderboard -> viewModel.navigator.actionNavigateToLeaderboard()
            else -> viewModel.intents().offer(intent)
        }
    }
}
