package com.maxcruz.player.presentation.waiting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.maxcruz.player.presentation.waiting.mvi.WaitingIntent

@Composable
fun WaitingView(
    viewModel: WaitingViewModel,
    code: String,
) {
    // Load the view
    viewModel.load(code = code)

    // Render and offer intents
    val viewState = remember(viewModel) { viewModel.states() }.collectAsState().value
    viewState.Render { intent ->
        when (intent) {
            is WaitingIntent.CloseGame -> viewModel.navigator.actionNavigateUp()
            is WaitingIntent.Load -> viewModel.intents().offer(intent)
        }
    }
}

