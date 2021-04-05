package com.maxcruz.player.presentation.waiting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

@Composable
fun WaitingView(
    viewModel: WaitingViewModel,
    code: String,
) {
    // Load the view
    viewModel.load(code = code)

    // Render and offer intents
    val viewState = remember(viewModel) { viewModel.states() }.collectAsState().value
    viewState.Render { viewModel.intents().offer(it) }
}

