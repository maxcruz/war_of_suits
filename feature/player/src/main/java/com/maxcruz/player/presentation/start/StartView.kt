package com.maxcruz.player.presentation.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.maxcruz.design.theme.WarOfSuitsTheme
import com.maxcruz.player.domain.model.Player
import com.maxcruz.player.presentation.start.mvi.StartNavigation
import com.maxcruz.player.presentation.start.mvi.StartViewState

@Composable
fun StartView(
    viewModel: StartViewModel,
    actionNavigateToGame: (String) -> Unit,
    actionNavigateToWaiting: (String) -> Unit,
    actionNavigateToJoin: (String) -> Unit,
    actionNavigateToLeaderboard: () -> Unit,
) {
    val viewState = remember(viewModel) { viewModel.states() }.collectAsState()
    val enqueuedNavigation = viewState.value.dequeueNavigation()

    // Perform navigation state
    enqueuedNavigation?.let {
        when (it) {
            is StartNavigation.OpenLeaderboard -> actionNavigateToLeaderboard()
            is StartNavigation.OpenNewGame ->
                when (it.player) {
                    is Player.FirstPlayer -> actionNavigateToWaiting(it.player.userId)
                    is Player.SecondPlayer -> actionNavigateToJoin(it.player.userId)
                }
            is StartNavigation.OpenStartedGame -> actionNavigateToGame(it.sessionId)
        }
    }

    // Receive and display the view state. Process user intents and navigation intents
    viewState.value.Render { viewModel.intents().offer(it) }
}

@Preview
@Composable
private fun DefaultPreview() {
    WarOfSuitsTheme {
        StartViewState(
            isLoading = false,
            hasError = false
        ).Render {}
    }
}
