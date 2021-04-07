package com.maxcruz.player.presentation.join

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import com.maxcruz.player.presentation.join.mvi.JoinIntent
import androidx.compose.runtime.getValue

@Composable
fun JoinView(viewModel: JoinViewModel) {
    val viewState by remember(viewModel) { viewModel.states() }.collectAsState()
    viewState.Render { intent ->
        when (intent) {
            is JoinIntent.CloseGame -> viewModel.navigator.actionNavigateUp()
            is JoinIntent.InputCode -> viewModel.intents().offer(intent)
        }
    }
}
