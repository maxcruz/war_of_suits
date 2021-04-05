package com.maxcruz.player.presentation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

@Composable
fun StartView(viewModel: StartViewModel) {
    // Receive and display the view state. Process user intents and navigation intents
    val viewState = remember(viewModel) { viewModel.states() }.collectAsState().value
    viewState.Render { intent ->
        val intentChannel = viewModel.intents()
        intentChannel.offer(intent)
    }
}
